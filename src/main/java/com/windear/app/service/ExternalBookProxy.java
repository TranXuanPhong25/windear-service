package com.windear.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Qualifier("externalBookProxy")
public class ExternalBookProxy implements ExternalBookService {
    private final ExternalBookService externalBookService;

    @Autowired
    public ExternalBookProxy(@Qualifier("externalBookReal") ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @Override
    @Cacheable(value = "basicGenres")
    public String getBasicGenres() {
        return externalBookService.getBasicGenres();
    }

    @Override
    @Cacheable(value = "taggedBooks", key = "#tagName")
    public String getTaggedBooks(String tagName) {
        return externalBookService.getTaggedBooks(tagName);
    }

    @Override
    @Cacheable(value = "reviews", key = "#workId")
    public String getReviews(String workId) {
        return externalBookService.getReviews(workId);
    }

    @Override
    @Cacheable(value = "bookByLegacyId", key = "#legacyId")
    public String getBookByLegacyId(int legacyId) {
        return externalBookService.getBookByLegacyId(legacyId);
    }

    @Override
    @Cacheable(value = "searchSuggestions", key = "#q")
    public String getSearchSuggestions(String q) {
        return externalBookService.getSearchSuggestions(q);
    }

    @Override
    @Cacheable(value = "popularBooks")
    public String getPopularBookLists() {
        return externalBookService.getPopularBookLists();
    }

    @Override
    @Cacheable(value = "similarBooks", key = "#id")
    public String getSimilarBooks(String id) {
        return externalBookService.getSimilarBooks(id);
    }

    @Override
    @Cacheable(value = "editions", key = "#id")
    public String getEditions(String id) {
        return externalBookService.getEditions(id);
    }

    @Override
    @Cacheable(value = "isbnExist", key = "#isbn")
    public Boolean isIsbnExist(String isbn) {
        return externalBookService.isIsbnExist(isbn);
    }
}
