package com.windear.app.controller;

import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;
import com.windear.app.service.PopularBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PopularBookController {
    private final PopularBookService bookService;

    @Autowired
    public PopularBookController(PopularBookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/popular-book")
    public PopularBook add(@RequestBody PopularBook book) {
        return bookService.addBook(book);
    }

    @GetMapping("/popular-book/{id}")
    public PopularBook findById(@PathVariable Integer id) {
        return bookService.findById(id);
    }

    @GetMapping("/popular-book/top10")
    public List<InternalBook> findTop10ByScore() {
        return bookService.findTop10ByScore();
    }

    @PutMapping("/popular-book/{bookId}/{score}")
    public PopularBook updateScore(@PathVariable Integer bookId, @PathVariable Integer score) {
        return bookService.updateScore(bookId, score);
    }

    @DeleteMapping("/popular-book/{id}")
    public void delete(@PathVariable Integer id) {
        bookService.delete(id);
    }
}
