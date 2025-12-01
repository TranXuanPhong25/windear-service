package com.windear.app.service;

import com.windear.app.entity.BookInShelf;
import com.windear.app.entity.Shelf;
import com.windear.app.entity.Shelves;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.repository.ShelvesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShelvesBoundaryTest {

    @Mock
    private ShelvesRepository shelvesRepository;

    @InjectMocks
    private ShelvesServiceImpl shelvesService;

    private Shelves shelves;
    private BookInShelf book;

    @BeforeEach
    void setUp() {
        shelves = new Shelves("user1");
        book = new BookInShelf(1, "Test Book", "Author", 4.5, "image.jpg", 
            java.time.LocalDate.now(), java.time.LocalDate.now(), null, 0, 0.0);
    }

    @Test
    void addBookToShelves_ShouldThrowException_WhenShelfNamesIsNull() {
        when(shelvesRepository.findById("user1")).thenReturn(Optional.of(shelves));

        assertThrows(Exception.class, () -> {
            shelvesService.addBookToShelves("user1", Arrays.asList(null, "Want to read"), book);
        });
    }

    @Test
    void addBookToShelves_ShouldThrowException_WhenBookIsNull() {
        when(shelvesRepository.findById("user1")).thenReturn(Optional.of(shelves));

        assertThrows(NullPointerException.class, () -> {
            shelvesService.addBookToShelves("user1", List.of("Want to read"), null);
        });
    }

    @Test
    void findShelvesByUserId_ShouldNotCreateNew_WhenUserIdIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            shelvesService.findShelvesByUserId(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shelvesService.findShelvesByUserId("");
        });
    }

    @Test
    void updateBookStatusInShelves_ShouldNotAccept_WhenBookStatusIsInvalid() {
        // Book status nên trong khoảng 0 1 2
        Shelf shelf = shelves.getShelfByName("Want to read");
        shelf.addBook(book);
        
        when(shelvesRepository.findById("user1")).thenReturn(Optional.of(shelves));
        when(shelvesRepository.save(any(Shelves.class))).thenReturn(shelves);

        assertThrows(IllegalArgumentException.class, () -> {
            shelvesService.updateBookStatusInShelves("user1", "Want to read", 1, -999);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shelvesService.updateBookStatusInShelves("user1", "Want to read", 1, Integer.MAX_VALUE);
        });
    }

    @Test
    void updateShelfName_ShouldThrowException_WhenNewNameIsInvalid() {
        when(shelvesRepository.findById("user1")).thenReturn(Optional.of(shelves));
        when(shelvesRepository.save(any(Shelves.class))).thenReturn(shelves);

        assertThrows(IllegalArgumentException.class, () -> {
            shelvesService.updateShelfName("user1", "Want to read", null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            shelvesService.updateShelfName("user1", "Want to read", "");
        });
    }

    @Test
    void getShelfByName_ShouldBeCaseSensitive_CreatesNewShelf() {
        // Tạo shelf mới thành "WANT TO READ" thay vì dùng "Want to read"
        when(shelvesRepository.findById("user1")).thenReturn(Optional.of(shelves));
        when(shelvesRepository.save(any(Shelves.class))).thenReturn(shelves);

        shelvesService.addBookToShelves("user1", Arrays.asList("WANT TO READ"), book);

        assertTrue(shelves.getShelfByName("Want to read").getBooks().contains(book));
    }
}
