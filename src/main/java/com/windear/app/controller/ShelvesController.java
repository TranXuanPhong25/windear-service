package com.windear.app.controller;

import com.windear.app.dto.AddBookRequest;
import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelves;
import com.windear.app.service.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelves")
public class ShelvesController {
    private ShelvesService shelvesService;

    @Autowired
    public ShelvesController(ShelvesService shelvesService) {
        this.shelvesService = shelvesService;
    }

    @PostMapping("/add")
    public Shelves addShelves(@RequestBody Shelves shelves) {
        return shelvesService.addShelves(shelves);
    }

    @GetMapping("")
    public List<Shelves> getAllShelves() {
        return shelvesService.getAll();
    }

    @PostMapping("/{userId}")
    public Shelves addBookToShelf(@RequestBody AddBookRequest request,
                                  @PathVariable String userId) {
        return shelvesService.addBookToShelf(userId, request.getShelfName(), request.getBook());
    }

    @DeleteMapping("/{userId}")
    public void deleteShelves(@PathVariable String userId) {
        shelvesService.deleteShelves(userId);
    }
}
