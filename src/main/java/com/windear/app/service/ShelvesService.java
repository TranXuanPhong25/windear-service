package com.windear.app.service;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;

import java.util.List;
import java.util.Optional;

public interface ShelvesService {
    Shelves addBookToShelf(String userId, String shelfName, BookInShelf shelf);
    Shelves findShelvesByUserName(String name);
    Shelves addShelves(Shelves shelves);
    List<Shelves> getAll();
}
