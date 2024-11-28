package com.windear.app.service;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;

import java.util.List;

public interface ShelvesService {
    /**
     * Adds a book to the specified shelves of a user.
     *
     * @param userId the ID of the user
     * @param shelfNames the names of the shelves
     * @param shelf the book to add to the shelves
     * @return the updated Shelves
     */
    Shelves addBookToShelves(String userId, List<String> shelfNames, BookInShelf shelf);

    /**
     * Finds the shelves of a user by their ID.
     *
     * @param userId the ID of the user
     * @return the Shelves of the specified user
     */
    Shelves findShelvesByUserId(String userId);

    /**
     * Adds new shelves.
     *
     * @param shelves the shelves to add
     * @return the added Shelves
     */
    Shelves addShelves(Shelves shelves);

    /**
     * Deletes the shelves of a user by their ID.
     *
     * @param userId the ID of the user
     */
    void deleteShelvesByUserId(String userId);

    /**
     * Updates the status of a book in a specific shelf of a user.
     *
     * @param userId the ID of the user
     * @param shelfName the name of the shelf
     * @param bookId the ID of the book
     * @param bookStatus the new status of the book
     * @return the updated Shelves
     */
    Shelves updateBookStatusInShelves(String userId, String shelfName, int bookId, int bookStatus);

    /**
     * Retrieves all shelves.
     *
     * @return a list of all Shelves
     */
    List<Shelves> getAll();

    /**
     * Updates the name of a shelf for a user.
     *
     * @param userId the ID of the user
     * @param oldName the old name of the shelf
     * @param newName the new name of the shelf
     * @return the updated Shelves
     */
    Shelves updateShelfName(String userId, String oldName, String newName);

    /**
     * Deletes a shelf by its name for a user.
     *
     * @param userId the ID of the user
     * @param shelfName the name of the shelf to delete
     * @return the updated Shelves
     */
    Shelves deleteShelfByName(String userId, String shelfName);

    /**
     * Deletes a book from the specified shelves of a user.
     *
     * @param userId the ID of the user
     * @param shelfNames the names of the shelves
     * @param bookId the ID of the book to delete
     * @return the updated Shelves
     */
    Shelves deleteBookInShelves(String userId, List<String> shelfNames, int bookId);

    /**
     * Retrieves a shelf by the user ID and shelf name.
     *
     * @param userId the ID of the user
     * @param shelfName the name of the shelf
     * @return a list of Shelves for the specified user ID and shelf name
     */
    List<Shelf> getShelfByUserIdAndShelfName(String userId, String shelfName);

    /**
     * Retrieves all shelf names of a user.
     *
     * @param userId the ID of the user
     * @return a list of all shelf names of the specified user
     */
    List<String> getAllShelvesNamesOfUser(String userId);

    /**
     * Adds a new shelf with the specified name for a user.
     *
     * @param userId the ID of the user
     * @param shelfName the name of the new shelf
     * @return the updated Shelves
     */
    Shelves addShelfWithName(String userId, String shelfName);

    /**
     * Retrieves the names of shelves that contain a specific book for a user.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book
     * @return a list of shelf names that contain the specified book
     */
    List<String> getShelfNamesContainsBook(String userId, int bookId);
}
