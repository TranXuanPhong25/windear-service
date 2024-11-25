package com.windear.app.controller;

import com.windear.app.service.ExternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/search")
public class SearchController {
    private ExternalBookService externalBookService;

    @Autowired
    public SearchController(@Qualifier("externalBookProxy") ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @GetMapping()
    public String getSearchSuggestions(@RequestParam String q) {
        return externalBookService.getSearchSuggestions(q);
    }
}
