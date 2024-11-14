package com.windear.app.controller;

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

    @GetMapping("/books/search")
    public String findByTitle(@RequestParam String title,
                               @RequestParam(defaultValue = "0") int pageNo) {
        return externalBookService.findByTitle(title, pageNo);
    }


}
