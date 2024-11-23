package com.windear.app.controller;

import com.windear.app.dto.AddBookRequest;
import com.windear.app.dto.DeleteBookRequest;
import com.windear.app.dto.UpdateShelvesRequest;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;
import com.windear.app.service.ShelvesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelves")
public class ShelvesController {
    private final ShelvesService shelvesService;

    @Autowired
    public ShelvesController(ShelvesService shelvesService) {
        this.shelvesService = shelvesService;
    }

    @GetMapping("")
    public List<Shelves> getAllShelves() {
        return shelvesService.getAll();
    }

    @PostMapping("/{userId}")
    public Shelves addBookToShelf(@RequestBody AddBookRequest request,
                                  @PathVariable String userId) {
        return shelvesService.addBookToShelves(userId, request.getShelfNames(), request.getBook());
    }

    @DeleteMapping("/{userId}")
    public void deleteShelvesByUserId(@PathVariable String userId) {
        shelvesService.deleteShelvesByUserId(userId);
    }

    @PutMapping("/{userId}/mutate")
    public Shelves updateBookStatusInShelf(@RequestBody UpdateShelvesRequest request,
                                           @PathVariable String userId) {
        return shelvesService.updateBookStatusInShelves(userId, request.getShelfName(), request.getBookId(), request.getBookStatus());
    }

    @DeleteMapping("/{userId}/shelf")
    public Shelves deleteShelfByName(@PathVariable String userId,
                                    @RequestParam String name) {
        return shelvesService.deleteShelfByName(userId, name);
    }

    @PutMapping("/{userId}/shelf")
    public Shelves updateShelfName(@PathVariable String userId,
                                   @RequestParam String oldName,
                                   @RequestParam String newName) {
        return shelvesService.updateShelfName(userId, oldName, newName);
    }

    @DeleteMapping("/{userId}/book")
    public Shelves deleteBookInShelf(@PathVariable String userId,
                                     @RequestBody DeleteBookRequest request) {
        return shelvesService.deleteBookInShelves(userId, request.getShelfName(), request.getBookId());
    }

    @GetMapping("/{userId}/shelf")
    public List<Shelf> getShelfByUserIdAndShelfName(@PathVariable String userId,
                                  @RequestParam String name) {
        return shelvesService.getShelfByUserIdAndShelfName(userId, name);
    }

    @GetMapping("/{userId}")
    public List<String> getShelves(@PathVariable String userId) {
        return shelvesService.getAllShelvesNamesOfUser(userId);
    }

    @PostMapping("/{userId}/shelf")
    public Shelves addShelf(@PathVariable String userId,
                            @RequestParam String name) {
        return shelvesService.addShelfWithName(userId, name);
    }

    @GetMapping("/{userId}/{bookId}")
    public List<String> getShelfNamesContainsBook(@PathVariable String userId,
                                                  @PathVariable int bookId) {
        return shelvesService.getShelfNamesContainsBook(userId, bookId);
    }
}
