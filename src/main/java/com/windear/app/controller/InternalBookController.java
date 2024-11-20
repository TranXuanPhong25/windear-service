package com.windear.app.controller;

import com.windear.app.entity.Book;
import com.windear.app.entity.InternalBook;
import com.windear.app.service.InternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/db")
public class InternalBookController {
    private final InternalBookService bookService;

    @Autowired
    public InternalBookController(InternalBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<InternalBook> findAll() {
        return bookService.findAll();
    }

    @GetMapping("/books/{id}")
    public InternalBook findBookById(@PathVariable Integer id) {
        return bookService.findById(id);
    }

    @GetMapping("/books/new-releases")
    public List<InternalBook> findTop10ByReleaseDate() {
        return bookService.findTop10ByReleaseDate();
    }

    @GetMapping("books/count-last-30-day")
    public long getBookInLast30Day() {
        return bookService.getBookInLast30Day();
    }

    @PostMapping("/books")
    public InternalBook addBook(@RequestBody InternalBook book) {
        return bookService.add(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
    }
}