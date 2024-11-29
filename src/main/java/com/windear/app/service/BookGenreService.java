package com.windear.app.service;

import com.windear.app.entity.BookGenre;
import com.windear.app.primarykey.BookGenreId;

import java.util.List;

public interface BookGenreService {
    /**
     * Adds a new book genre.
     *
     * @param bookGenre the book genre to add
     * @return the added BookGenre
     */
    BookGenre add(BookGenre bookGenre);

    /**
     * Finds a book genre by its ID.
     *
     * @param id the ID of the book genre
     * @return the BookGenre with the specified ID
     */
    BookGenre findById(BookGenreId id);

    /**
     * Finds all book genres by the book ID.
     *
     * @param id the ID of the book
     * @return a list of BookGenres associated with the specified book ID
     */
    List<BookGenre> findAllByBookId(Integer id);

    /**
     * Deletes a book genre by its ID.
     *
     * @param id the ID of the book genre to delete
     */
    void delete(BookGenreId id);
}
