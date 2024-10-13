package com.windear.app.controller;

import com.windear.app.entity.Book;
import com.windear.app.service.BookService;
import com.windear.app.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookService bookServiceImpl;

    @Autowired
    public BookController(BookService bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        return bookServiceImpl.add(book);
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookServiceImpl.findAll();
    }

    @GetMapping("/books/{id}")
    public Book findBookById(@PathVariable int id) {
        return bookServiceImpl.findById(id);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable int id) {
        bookServiceImpl.delete(id);
    }
}