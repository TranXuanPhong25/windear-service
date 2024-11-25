package com.windear.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Qualifier("externalBookProxy")
public class ExternalBookProxy implements ExternalBookService {
    private ExternalBookService externalBookService;

    @Autowired
    public ExternalBookProxy(@Qualifier("externalBookReal") ExternalBookService externalBookService) {
        this.externalBookService = externalBookService;
    }

    @Override
    public String getBasicGenres() {
        return externalBookService.getBasicGenres();
    }

    @Override
    @Cacheable(value =  "taggedBooks", key = "#tagName")
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
    public String getFeaturedBookLists() {
        return externalBookService.getFeaturedBookLists();
    }

    @Override
    public String getPopularBookLists() {
        return externalBookService.getPopularBookLists();
    }

    @Override
    @Cacheable(key = "similarBooks", value = "#id")
    public String getSimilarBooks(String id) {
        return externalBookService.getSimilarBooks(id);
    }

    @Override
    @Cacheable(key = "editions", value = "#id")
    public String getEditions(String id) {
        return externalBookService.getEditions(id);
    }

    @Override
    @Cacheable(key = "isbnExist", value = "#isbn")
    public Boolean isIsbnExist(String isbn) {
        return externalBookService.isIsbnExist(isbn);
    }
}
