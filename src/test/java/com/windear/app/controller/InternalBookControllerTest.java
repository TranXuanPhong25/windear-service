package com.windear.app.controller;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.BookGenre;
import com.windear.app.entity.Genre;
import com.windear.app.entity.InternalBook;
import com.windear.app.primarykey.BookGenreId;
import com.windear.app.service.BookGenreService;
import com.windear.app.service.GenreService;
import com.windear.app.service.InternalBookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class InternalBookControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private InternalBookService bookService;

    @MockBean
    private BookGenreService bookGenreService;

    @MockBean
    private GenreService genreService;

    private InternalBook response;
    private InternalBook book2;
    List<InternalBook> books;
    AddInternalBookRequestDTO responseDto;
    List<InternalBookDTO> booksDto;
    LocalDate date = LocalDate.of(2024, 11, 20);
    @BeforeEach
    void InitData() {
        response = new InternalBook();
        response.setId(1);
        response.setTitle("Book Title 1");
        response.setAuthor("Author 1");
        response.setReleaseDate(date);
        response.setRating(4.2);
        response.setImageUrl("http://example.com/image1.jpg");
        response.setDescription("Description 1");
        response.setIsbn10("1234567890");
        response.setIsbn13("1234567890123");
        response.setAuthorDescription("Author Description 1");
        response.setPublisher("Publisher 1");
        response.setFormat("Hardcover");
        response.setLanguage("English");
        response.setNumPages(300);
        response.setAddDate(date);

        book2 = new InternalBook();
        book2.setId(2);
        book2.setTitle("Book Title 2");
        book2.setAuthor("Author 2");
        book2.setReleaseDate(LocalDate.of(2020, 5, 15));
        book2.setRating(3.8);
        book2.setImageUrl("http://example.com/image2.jpg");
        book2.setDescription("Description 2");
        book2.setIsbn10("0987654321");
        book2.setIsbn13("0987654321098");
        book2.setAuthorDescription("Author Description 2");
        book2.setPublisher("Publisher 2");
        book2.setFormat("Paperback");
        book2.setLanguage("French");
        book2.setNumPages(400);
        book2.setAddDate(LocalDate.of(2021, 6, 10));

        responseDto = new AddInternalBookRequestDTO();
        responseDto.setInternalBook(response);
        books = new ArrayList<>();
        booksDto = new ArrayList<>();
        books = Arrays.asList(response, book2);

    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void findBookByIdTest() throws Exception {
        BookGenre bookGenre1 = new BookGenre(new BookGenreId(1, 1));
        BookGenre bookGenre2 = new BookGenre(new BookGenreId(1, 2));

        List<BookGenre> bookGenres = Arrays.asList(bookGenre1, bookGenre2);

        Genre genre1 = new Genre();
        genre1.setId(1);
        genre1.setName("Fiction");

        Genre genre2 = new Genre();
        genre2.setId(2);
        genre2.setName("Adventure");

        Mockito.when(bookService.findById(anyInt())).thenReturn(responseDto);
        Mockito.when(bookGenreService.findAllByBookId(anyInt())).thenReturn(bookGenres);
        Mockito.when(genreService.findById(1)).thenReturn(genre1);
        Mockito.when(genreService.findById(2)).thenReturn(genre2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/db/books/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.title").value("Book Title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.releaseDate").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.rating").value(4.2))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.imageUrl").value("http://example.com/image1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.isbn10").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("internalBook.isbn13").value("1234567890123"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void findAllBook() throws Exception {
        InternalBookDTO book1 = convertToDTO(response);
        InternalBookDTO book3 = convertToDTO(book2);
        booksDto = Arrays.asList(book1, book3);
        Mockito.when(bookService.findAll()).thenReturn(booksDto);

        Mockito.when(bookService.convertToDTO(any(InternalBook.class)))
                .thenAnswer(invocation -> {
                    InternalBook book = invocation.getArgument(0);
                    InternalBookDTO dto = new InternalBookDTO();
                    dto.setId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setAuthor(book.getAuthor());
                    dto.setReleaseDate(book.getReleaseDate());
                    dto.setImageUrl(book.getImageUrl());
                    return dto;
                });

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/db/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book Title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].releaseDate").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageUrl").value("http://example.com/image1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book Title 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].releaseDate").value("2020-05-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].imageUrl").value("http://example.com/image2.jpg"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void findTop10ByReleaseDateTest() throws Exception {
        Mockito.when(bookService.findTop10ByReleaseDate()).thenReturn(books);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/db/books/new-releases")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book Title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].releaseDate").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageUrl").value("http://example.com/image1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book Title 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].releaseDate").value("2020-05-15"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].imageUrl").value("http://example.com/image2.jpg"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void getBookInLast30DayTest() throws Exception {
        Mockito.when(bookService.getBookInLast30Day()).thenReturn(Long.valueOf(123));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/db/books/count/last-30-day")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(String.valueOf(123)));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    void addBookTest() throws Exception {
        Mockito.when(bookService.add(any(AddInternalBookRequestDTO.class))).thenReturn(response);
        Mockito.when(bookGenreService.add(any(BookGenre.class))).thenReturn(new BookGenre(new BookGenreId(response.getId(), 1)));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/db/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                        { "internalBook": {
                             "title": "Book Title 1",
                             "author": "Author 1",
                             "releaseDate": "2024-11-20",
                             "rating": 4.2,
                             "imageUrl": "http://example.com/image1.jpg",
                             "description": "Description 1",
                             "isbn10": "1234567890",
                             "isbn13": "1234567890123",
                             "authorDescription": "Author Description 1",
                             "publisher": "Publisher 1",
                             "format": "Hardcover",
                             "language": "English",
                             "numPages": 300
                        },
                         "genres" : "1"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Book Title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("releaseDate").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("rating").value(4.2))
                .andExpect(MockMvcResultMatchers.jsonPath("imageUrl").value("http://example.com/image1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn10").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn13").value("1234567890123"));


    }

    @Test
    @WithMockUser(roles = "admin")
    void updateTest() throws Exception{
        response.setId(2);
        Mockito.when(bookService.update(any(AddInternalBookRequestDTO.class))).thenReturn(response);
        Mockito.when(bookGenreService.findAllByBookId(2)).thenReturn(new ArrayList<>());
        Mockito.doNothing().when(bookGenreService).delete(new BookGenreId(2, 1));
        Mockito.when(bookGenreService.add(new BookGenre(new BookGenreId(1, 2)))).thenReturn(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/db/books/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                        { "internalBook": {
                             "title": "Book Title 1",
                             "author": "Author 1",
                             "releaseDate": "2024-11-20",
                             "rating": 4.2,
                             "imageUrl": "http://example.com/image1.jpg",
                             "description": "Description 1",
                             "isbn10": "1234567890",
                             "isbn13": "1234567890123",
                             "authorDescription": "Author Description 1",
                             "publisher": "Publisher 1",
                             "format": "Hardcover",
                             "language": "English",
                             "numPages": 300
                        },
                         "genres" : ""
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("title").value("Book Title 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("author").value("Author 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("releaseDate").value("2024-11-20"))
                .andExpect(MockMvcResultMatchers.jsonPath("rating").value(4.2))
                .andExpect(MockMvcResultMatchers.jsonPath("imageUrl").value("http://example.com/image1.jpg"))
                .andExpect(MockMvcResultMatchers.jsonPath("description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn10").value("1234567890"))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn13").value("1234567890123"));



    }


    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    void deleteTest() throws Exception {
        Mockito.doNothing().when(bookService).delete(anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/db/books/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"admin"})
    void deleteTest_NotFound() throws Exception {
        Mockito.doThrow(new RuntimeException()).when(bookService).delete(anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/db/books/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    public InternalBookDTO convertToDTO(InternalBook book) {
        InternalBookDTO dto = new InternalBookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setAddDate(book.getAddDate());
        dto.setReleaseDate(book.getReleaseDate());
        dto.setImageUrl(book.getImageUrl());
        return dto;
    }
}

