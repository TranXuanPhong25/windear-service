package com.windear.app.controller;

import com.windear.app.entity.Book;
import com.windear.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/db")
public class DatabaseBookController {
    private final BookService bookService;

    @Autowired
    public DatabaseBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.add(book);
    }

    @GetMapping("/books/{id}")
    public List<Book> findBookById(@PathVariable String id) {
        return bookService.findById(id);
    }
}