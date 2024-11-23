package com.windear.app.service;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;

import java.util.List;

public interface ShelvesService {
    Shelves addBookToShelf(String userId, String shelfName, BookInShelf shelf);
    Shelves findShelvesByUserId(String name);
    Shelves addShelves(Shelves shelves);
    void deleteShelvesByUserId(String userId);
    Shelves updateBookStatusInShelves(String userId, String shelfName, int bookId, int bookStatus);
    List<Shelves> getAll();
    Shelves updateShelfName(String userId, String oldName, String newName);
    Shelves deleteShelfByName(String userId, String shelfName);
    Shelves deleteBookInShelves(String userId, String shelfName, int bookId);
    List<Shelf> getShelfByUserIdAndShelfName(String userId, String shelfName);
    List<String> getAllShelvesNamesOfUser(String userId);
    Shelves addShelfWithName(String userId, String shelfName);
    List<String> getShelfNamesContainsBook(String userId, int bookId);
}
