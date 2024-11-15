package com.windear.app.service;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.repository.ShelvesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelvesServiceImpl implements ShelvesService {
    private ShelvesRepository shelvesRepository;

    @Autowired
    public ShelvesServiceImpl(ShelvesRepository shelvesRepository) {
        this.shelvesRepository = shelvesRepository;
    }

    @Override
    public Shelves addBookToShelf(String userId, String shelfName, BookInShelf book) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        shelf.addBook(book);
        System.out.println(shelf);
        return shelvesRepository.save(shelves);
    }

    @Override
    public Shelves findShelvesByUserId(String userId) {
        Optional<Shelves> shelves = shelvesRepository.findById(userId);
        if (shelves.isPresent()) {
            return shelves.get();
        } else {
            return new Shelves(userId);
        }
    }

    @Override
    public Shelves addShelves(Shelves shelves) {
        return shelvesRepository.save(shelves);
    }

    @Override
    public void deleteShelves(String userId) {
        shelvesRepository.deleteById(userId);
    }

    @Override
    public List<Shelves> getAll() {
        return shelvesRepository.findAll();
    }
}
