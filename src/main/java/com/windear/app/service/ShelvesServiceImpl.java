package com.windear.app.service;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.repository.ShelvesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShelvesServiceImpl implements ShelvesService {
    private final ShelvesRepository shelvesRepository;

    @Autowired
    public ShelvesServiceImpl(ShelvesRepository shelvesRepository) {
        this.shelvesRepository = shelvesRepository;
    }

    @Override
    public Shelves addBookToShelf(String userId, String shelfName, BookInShelf book) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        shelf.addBook(book);
        return shelvesRepository.save(shelves);
    }

    @Override
    public Shelves findShelvesByUserId(String userId) {
        Optional<Shelves> shelves = shelvesRepository.findById(userId);
        return shelves.orElseGet(() -> new Shelves(userId));
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
    public Shelves updateBookStatusInShelves(String userId, String shelfName, int bookId, int bookStatus) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        BookInShelf book = shelf.findBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Cannot found book with id: " + bookId + " in " + shelfName + " shelf belonging to user with userId: " + userId);
        }
        book.setBookStatus(bookStatus);
        return shelvesRepository.save(shelves);
    }

    @Override
    public List<Shelves> getAll() {
        return shelvesRepository.findAll();
    }

    @Override
    public Shelves deleteShelfByName(String userId, String shelfName) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        shelves.getShelves().remove(shelf);
        System.out.println(shelfName);
        return shelvesRepository.save(shelves);
    }

    @Override
    public Shelves deleteBookInShelves(String userId, String shelfName, int bookId) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        BookInShelf book = shelf.findBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException("Cannot found book with id: " + bookId + " in " + shelfName + " shelf belonging to user with userId: " + userId);
        }
        shelf.getBooks().remove(book);
        return shelvesRepository.save(shelves);
    }

    @Override
    public List<Shelf> getShelves(String userId, String shelfName) {
        Shelves shelves = findShelvesByUserId(userId);
        if (shelfName.equals("all")) {
            return shelves.getShelves();
        } else {
            List<Shelf> listOfShelf = new ArrayList<>();
            listOfShelf.add(shelves.getShelfByName(shelfName));
            return listOfShelf;
        }
    }

    @Override
    public List<String> getAllShelvesNames(String userId) {
        Shelves shelves = findShelvesByUserId(userId);
        return shelves.getShelves().stream().map(Shelf::getName).collect(Collectors.toList());
    }

    @Override
    public Shelves addShelfWithName(String userId, String shelfName) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        return shelvesRepository.save(shelves);
    }
}
