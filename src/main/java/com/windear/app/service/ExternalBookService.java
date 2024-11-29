package com.windear.app.service;

import org.springframework.stereotype.Service;

@Service
public interface ExternalBookService {
    /**
     * Retrieves the basic genres.
     *
     * @return a string representing the basic genres
     */
    String getBasicGenres();

    /**
     * Retrieves books tagged with the specified tag name.
     *
     * @param tagName the name of the tag
     * @return a string representing the tagged books
     */
    String getTaggedBooks(String tagName);

    /**
     * Retrieves reviews for the specified work ID.
     *
     * @param workId the ID of the work
     * @return a string representing the reviews
     */
    String getReviews(String workId);

    /**
     * Retrieves a book by its legacy ID.
     *
     * @param legacyId the legacy ID of the book
     * @return a string representing the book
     */
    String getBookByLegacyId(int legacyId);

    /**
     * Retrieves search suggestions based on the query.
     *
     * @param q the search query
     * @return a string representing the search suggestions
     */
    String getSearchSuggestions(String q);

    /**
     * Retrieves popular book lists.
     *
     * @return a string representing the popular book lists
     */
    String getPopularBookLists();

    /**
     * Retrieves similar books for the specified book ID.
     *
     * @param id the ID of the book
     * @return a string representing the similar books
     */
    String getSimilarBooks(String id);

    /**
     * Retrieves editions of the specified book ID.
     *
     * @param id the ID of the book
     * @return a string representing the editions
     */
    String getEditions(String id);

    /**
     * Checks if the specified ISBN exists in third party API.
     *
     * @param isbn the ISBN to check
     * @return true if the ISBN exists, false otherwise
     */
    Boolean isIsbnExist(String isbn);
}
