package com.springsecurity.sample.library.service;

import com.springsecurity.sample.library.entity.Book;
import com.springsecurity.sample.library.repo.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBook(String genre) {
        List<Book> books = bookRepository.findByGenre(genre);
        return books;
    }

    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }
}
