package com.windear.app.controller;

import com.windear.app.entity.InternalBook;
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

    @GetMapping("/popular-book/top10")
    public List<InternalBook> findTop10ByScore() {
        return bookService.findTop10ByScore();
    }
}
