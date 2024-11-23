package com.windear.app.controller;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.Book;
import com.windear.app.entity.BookGenre;

import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.InternalBook;
import com.windear.app.primarykey.BookGenreId;
import com.windear.app.service.BookGenreService;
import com.windear.app.service.InternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/db")
public class InternalBookController {
    private final InternalBookService bookService;
    private final BookGenreService bookGenreService;

    @Autowired
    public InternalBookController(InternalBookService bookService, BookGenreService bookGenreService) {
        this.bookService = bookService;
        this.bookGenreService = bookGenreService;
    }

    @GetMapping("/books")
    public List<InternalBookDTO> findAll() {
        List<InternalBook> books = bookService.findAll();
        List<InternalBookDTO> booksDto = new ArrayList<>();
        for(InternalBook it : books) {
            booksDto.add(convertToDTO(it));
        }
        return booksDto;
    }

    @GetMapping("/books/{id}")
    public AddInternalBookRequestDTO findBookById(@PathVariable Integer id) {
        AddInternalBookRequestDTO book = new AddInternalBookRequestDTO();
        InternalBook bookFromDb = bookService.findById(id);
        List<BookGenre> bookGenres = bookGenreService.findAllByBookId(id);
        String genres = "";
        for(int i = 0; i < bookGenres.size(); i++) {
            if(i < bookGenres.size() - 1) {
                genres += String.valueOf(bookGenres.get(i).getBookGenreId().getGenreId()) + ',';
            } else {
                genres += String.valueOf(bookGenres.get(i).getBookGenreId().getGenreId());
            }
        }
        book.setGenres(genres);
        book.setInternalBook(bookFromDb);
        return book;
    }

    @GetMapping("/books/new-releases")
    public List<InternalBook> findTop10ByReleaseDate() {
        return bookService.findTop10ByReleaseDate();
    }

    @GetMapping("/books/count/last-30-day")
    public long getBookInLast30Day() {
        return bookService.getBookInLast30Day();
    }

    @PostMapping("/books")
    public InternalBook addBook(@RequestBody AddInternalBookRequestDTO request) {
        InternalBook book = bookService.add(request.getInternalBook());
        String[] genreIds = request.getGenres().split(",");
        for (String genreId : genreIds) {
            BookGenre bookGenre = new BookGenre();
            BookGenreId bookGenreId = new BookGenreId(book.getId(), Integer.parseInt(genreId.trim()));
            bookGenre.setBookGenreId(bookGenreId);
            bookGenreService.add(bookGenre);
        }
        return book;
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
    }

    private InternalBookDTO convertToDTO(InternalBook book) {
        InternalBookDTO dto = new InternalBookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setAddDate(book.getAddDate());
        dto.setReleaseDate(book.getReleaseDate());
        return dto;
    }
}