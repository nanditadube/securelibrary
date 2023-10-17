package com.springsecurity.sample.library.config;

import com.springsecurity.sample.library.exception.UserAuthenticationErrorHandler;
import com.springsecurity.sample.library.exception.UserForbiddenErrorHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(BasicAuthentication.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final BasicAuthentication authentication;

    public SecurityConfiguration(BasicAuthentication authentication) {
        this.authentication = authentication;
    }

    /*@Bean
    @Order(1)
    public SecurityFilterChain bookFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .antMatcher("/library/**")
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/library/**").hasRole("USER").anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(userAuthenticationErrorHandler())
                        .accessDeniedHandler(new UserForbiddenErrorHandler()));

        return http.build();
    }*/

    @Bean
    public AuthenticationEntryPoint userAuthenticationErrorHandler() {
        UserAuthenticationErrorHandler userAuthenticationErrorHandler =
                new UserAuthenticationErrorHandler();
        userAuthenticationErrorHandler.setRealmName("Basic user Authentication");
        return userAuthenticationErrorHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("libuser").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("user123")).roles("USER")
                .and()
                .withUser("libadmin").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin123")).roles("ADMIN", "USER");
    }

}