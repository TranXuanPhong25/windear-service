package com.windear.app.service;

import org.springframework.stereotype.Service;

@Service
public interface ExternalBookService {
    String getBasicGenres();
    String getTaggedBooks(String tagName);
    String getReviews(String workId);
    String getBookByLegacyId(int id);
    String getSearchSuggestions(String q);
    String getFeaturedBookLists();
    String getPopularBookLists();
    String getSimilarBooks(String id);
    String getEditions(String id);
}
