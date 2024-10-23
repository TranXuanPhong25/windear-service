package com.windear.app.controller;

import com.windear.app.entity.ExternalBook;
import com.windear.app.service.ExternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/external")
public class ExternalBookController {
    private ExternalBookService externalBookService;

    @Autowired
    public ExternalBookController(ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @GetMapping("/books/{id}")
    public List<ExternalBook> findById(@PathVariable int id) {
        return externalBookService.findById(id);
    }

    @GetMapping("/books/search")
    public List<ExternalBook> findByTitle(@RequestParam String title) {
        return externalBookService.findByTitle(title);
    }


}
