package com.windear.app.service;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelves;

import java.util.List;

public interface ShelvesService {
    Shelves addBookToShelf(String userId, String shelfName, BookInShelf shelf);
    Shelves findShelvesByUserId(String name);
    Shelves addShelves(Shelves shelves);
    void deleteShelves(String userId);
    List<Shelves> getAll();
}
