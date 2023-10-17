package com.springsecurity.sample.library.controller;

import com.springsecurity.sample.library.entity.Book;
import com.springsecurity.sample.library.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/library/books")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Book>> getBooks(@RequestParam String genre, @RequestParam String user) {
        return ResponseEntity.ok().body(bookService.getBook(genre));
    }

    @GetMapping("/library/books/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }
}
