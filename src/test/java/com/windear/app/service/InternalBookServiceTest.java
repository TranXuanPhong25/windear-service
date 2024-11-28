package com.windear.app.service;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.InternalBook;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.repository.InternalBookRepository;
import com.windear.app.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class InternalBookServiceTest {
    @Autowired
    private InternalBookService internalBookService;

    @MockBean
    private InternalBookRepository internalBookRepository;

    @MockBean
    private ReviewRepository reviewRepository;


    @MockBean
    private PopularBookService popularBookService;

    private InternalBook internalBook;
    private LocalDate addDate;

    @BeforeEach
    void initData() {
        addDate = LocalDate.of(2024, 11, 20);
        internalBook = new InternalBook();
        internalBook.setId(1);
        internalBook.setTitle("Sample Book");
        internalBook.setAuthor("Author Name");
        internalBook.setReleaseDate(LocalDate.of(2023, 1, 15));
        internalBook.setRating(0);
        internalBook.setAddDate(addDate);
    }

    @Test
    void findByIdTest() {
        Integer bookId = 1;
        Mockito.when(internalBookRepository.findById(bookId)).thenReturn(Optional.of(internalBook));
        Mockito.when(reviewRepository.getAverageRatingOfBookById(bookId)).thenReturn(4.5);

        AddInternalBookRequestDTO result = internalBookService.findById(bookId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(4.5, result.getInternalBook().getRating());
    }

    @Test
    void findByIdTest_NoRating() {
        Integer bookId = 2;
        Mockito.when(internalBookRepository.findById(bookId)).thenReturn(Optional.of(internalBook));
        Mockito.when(reviewRepository.getAverageRatingOfBookById(bookId)).thenReturn(null);

        AddInternalBookRequestDTO result = internalBookService.findById(bookId);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(0, result.getInternalBook().getRating());
    }

    @Test
    void findByIdTest_NotExist() {
        Integer bookId = 3;
        Mockito.when(internalBookRepository.findById(bookId)).thenReturn(Optional.empty());

        BookNotFoundException exception = Assertions.assertThrows(BookNotFoundException.class, () -> {
            internalBookService.findById(bookId);
        });

        Assertions.assertEquals("Book with id not found: " + bookId, exception.getMessage());
    }

    @Test
    void findAllShouldReturnListOfBooks() {
        InternalBook book1;
        book1 = new InternalBook();
        book1.setId(1);
        book1.setTitle("Sample Book 1");
        book1.setAuthor("Author 1");

        InternalBook book2 = new InternalBook();
        book2.setId(2);
        book2.setTitle("Sample Book 2");
        book2.setAuthor("Author 2");
        List<InternalBook> books = Arrays.asList(book1, book2);
        Mockito.when(internalBookRepository.findAll()).thenReturn(books);

        List<InternalBookDTO> result = internalBookService.findAll();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("Sample Book 1", result.get(0).getTitle());
        Assertions.assertEquals("Sample Book 2", result.get(1).getTitle());

    }



}
