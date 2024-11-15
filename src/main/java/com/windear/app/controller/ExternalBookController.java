package com.windear.app.controller;

import com.windear.app.service.ExternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String getReviews(@RequestBody String wordId) {
        return externalBookService.getReviews(wordId);
    }

    @GetMapping("/books/{id}")
    public String getBookByLegacyId(@PathVariable int id) {
        return externalBookService.getBookByLegacyId(id);
    }

    @GetMapping("/books/search")
    public String getSearchSuggestions(@RequestParam String q) {
        return externalBookService.getSearchSuggestions(q);
    }

    @GetMapping("/list/featured")
    public String getFeaturedBookLists() {
        return externalBookService.getFeaturedBookLists();
    }

    @GetMapping("/list/popular")
    public String getPopularBookLists() {
        return externalBookService.getPopularBookLists();
    }

    @GetMapping("/books/{id}/similar")
    public String getSimilarBooks(@PathVariable int id) {
        return externalBookService.getSimilarBooks(id);
    }
}
