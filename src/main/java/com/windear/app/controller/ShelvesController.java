package com.windear.app.controller;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;
import com.windear.app.service.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelves")
public class ShelvesController {
    @Autowired
    private ShelvesService shelvesService;

    @GetMapping("")
    public List<Shelves> getAllShelves() {
        return shelvesService.getAll();
    }

    @PostMapping("/add")
    public Shelves addShelves(@RequestBody Shelves shelves) {
        return shelvesService.addShelves(shelves);
    }

    @PostMapping("/{userId}/{shelfName}")
    public Shelves addBookToShelf(@RequestBody BookInShelf book,
                                @PathVariable String shelfName,
                                @PathVariable String userId) {
        return shelvesService.addBookToShelf(userId, shelfName, book);
    }
}
