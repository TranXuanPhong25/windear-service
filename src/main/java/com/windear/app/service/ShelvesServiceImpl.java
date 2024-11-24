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
    public Shelves addBookToShelves(String userId, List<String> shelfNames, BookInShelf book) {
        Shelves shelves = findShelvesByUserId(userId);
        for (String shelfName : shelfNames) {
            if (shelfName.equals("Want to read") || shelfName.equals("Currently reading") || shelfName.equals("Read")) {
                Shelf shelf1 = shelves.getShelfByName("Want to read");
                shelf1.getBooks().removeIf(b -> b.getId().equals(book.getId()));
                Shelf shelf2 = shelves.getShelfByName("Currently reading");
                shelf2.getBooks().removeIf(b -> b.getId().equals(book.getId()));
                Shelf shelf3 = shelves.getShelfByName("Read");
                shelf3.getBooks().removeIf(b -> b.getId().equals(book.getId()));
            }
            Shelf shelf = shelves.getShelfByName(shelfName);
            if (shelf.getBooks().stream().noneMatch(b -> b.getId().equals(book.getId()))) {
                shelf.addBook(book);
            }
        }
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
    public void deleteShelvesByUserId(String userId) {
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
    public Shelves updateShelfName(String userId, String oldName, String newName) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(oldName);
        if (getAllShelvesNamesOfUser(userId).stream().anyMatch(s -> s.equals(newName))) {
            throw new IllegalArgumentException("Shelf with name " + newName + " already exists.");
        }
        shelf.setName(newName);
        return addShelves(shelves);
    }

    @Override
    public Shelves deleteShelfByName(String userId, String shelfName) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        shelves.getShelves().remove(shelf);
        return shelvesRepository.save(shelves);
    }

    @Override
    public Shelves deleteBookInShelves(String userId, List<String> shelfNames, int bookId) {
        Shelves shelves = findShelvesByUserId(userId);
        for (String shelfName : shelfNames) {
            Shelf shelf = shelves.getShelfByName(shelfName);
            BookInShelf book = shelf.findBookById(bookId);
            if (book != null) {
                shelf.getBooks().remove(book);
                if (shelf.getBooks().isEmpty() && !shelf.isDefaultShelf()) {
                    shelves.getShelves().remove(shelf);
                }
            }
        }
        return shelvesRepository.save(shelves);
    }

    @Override
    public List<Shelf> getShelfByUserIdAndShelfName(String userId, String shelfName) {
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
    public List<String> getAllShelvesNamesOfUser(String userId) {
        Shelves shelves = findShelvesByUserId(userId);
        return shelves.getShelves().stream().map(Shelf::getName).collect(Collectors.toList());
    }

    @Override
    public Shelves addShelfWithName(String userId, String shelfName) {
        Shelves shelves = findShelvesByUserId(userId);
        Shelf shelf = shelves.getShelfByName(shelfName);
        return shelvesRepository.save(shelves);
    }

    @Override
    public List<String> getShelfNamesContainsBook(String userId, int bookId) {
        Shelves shelves = findShelvesByUserId(userId);
        List<String> shelfNames = new ArrayList<>();
        for (Shelf shelf : shelves.getShelves()) {
            if (shelf.findBookById(bookId) != null) {
                shelfNames.add(shelf.getName());
            }
        }
        return shelfNames;
    }
}
