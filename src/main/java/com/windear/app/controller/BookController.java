package com.windear.app.controller;

import com.windear.app.entity.Book;
import com.windear.app.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookService.add(book);
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book book) {
        return bookService.update(book);
    }

    @GetMapping("/books/{id}")
    public Book findBookById(@PathVariable int id) {
        return bookService.findById(id);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.delete(id);
    }
}