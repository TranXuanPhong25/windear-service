package com.windear.app.service;

import org.springframework.stereotype.Service;

@Service
public interface ExternalBookService {
    String getBasicGenres();
    String getTaggedBooks(String tagName);
    String getReviews();
    String getBookByLegacyId(String id);
    String getSearchSuggestions(String q);
    String getFeaturedBookLists();
    String getPopularBookLists();
    String getSimilarBooks(String id);
}
