package com.windear.app.controller;

import com.windear.app.service.ExternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/external")
public class ExternalBookController {
    private ExternalBookService externalBookService;

    @Autowired
    public ExternalBookController(ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @GetMapping("/tags")
    public String getBasicGenres() {
        return externalBookService.getBasicGenres();
    }

    @GetMapping("tag/{tagName}")
    public String getTaggedBooks(@PathVariable String tagName) {
        return externalBookService.getTaggedBooks(tagName);
    }

    @GetMapping("/reviews")
    public String getReviews(@RequestBody String workId) {
        return externalBookService.getReviews(workId);
    }

    @GetMapping("/books/{id}")
    public String getBookByLegacyId(@PathVariable int id) {
        return externalBookService.getBookByLegacyId(id);
    }

    @GetMapping("/list/featured")
    public String getFeaturedBookLists() {
        return externalBookService.getFeaturedBookLists();
    }

    @GetMapping("/list/popular")
    public String getPopularBookLists() {
        return externalBookService.getPopularBookLists();
    }

    @PostMapping("/similar")
    public String getSimilarBooks(@RequestBody Map<String,String> payload) {
        return externalBookService.getSimilarBooks(payload.get("data"));
    }
}
