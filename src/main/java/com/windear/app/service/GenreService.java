package com.windear.app.service;

import com.windear.app.entity.Genre;

import java.util.List;

public interface GenreService {
    /**
     * Adds a new genre.
     *
     * @param genre the genre to add
     * @return the added Genre
     */
    Genre add(Genre genre);

    /**
     * Finds a genre by its ID.
     *
     * @param id the ID of the genre
     * @return the Genre with the specified ID
     */
    Genre findById(Integer id);

    /**
     * Finds all genres.
     *
     * @return a list of all Genres
     */
    List<Genre> findAll();

    /**
     * Deletes a genre by its ID.
     *
     * @param id the ID of the genre to delete
     */
    void delete(Integer id);
}
