package com.windear.app.service;


import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;

import java.util.List;

public interface PopularBookService {
    /**
     * Finds a popular book by its ID.
     *
     * @param bookId the ID of the popular book
     * @return the PopularBook with the specified ID
     */
    PopularBook findById(Integer bookId);

    /**
     * Finds the top 10 internal books by score.
     *
     * @return a list of the top 10 InternalBooks by score
     */
    List<InternalBook> findTop10ByScore();

    /**
     * Adds a new popular book.
     *
     * @param book the popular book to add
     */
    void addBook(PopularBook book);

    /**
     * Updates the score of a popular book by its ID.
     *
     * @param bookId the ID of the popular book
     * @param score the new score of the popular book
     */
    void updateScore(Integer bookId, Integer score);

    /**
     * Deletes a popular book by its ID.
     *
     * @param bookId the ID of the popular book to delete
     */
    void delete(Integer bookId);
}
