package com.windear.app.controller;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.BookGenre;
import com.windear.app.entity.InternalBook;
import com.windear.app.primarykey.BookGenreId;
import com.windear.app.service.BookGenreService;
import com.windear.app.service.GenreService;
import com.windear.app.service.InternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/db")
public class InternalBookController {
    private final InternalBookService bookService;
    private final BookGenreService bookGenreService;
    private final GenreService genreService;

    @Autowired
    public InternalBookController(InternalBookService bookService, BookGenreService bookGenreService, GenreService genreService) {
        this.bookService = bookService;
        this.bookGenreService = bookGenreService;
        this.genreService = genreService;
    }

    @GetMapping("/books")
    public List<InternalBookDTO> findAll() {

        return bookService.findAll();

    }

    @GetMapping("/books/{id}")
    public AddInternalBookRequestDTO findBookById(@PathVariable Integer id) {

        return bookService.findById(id);

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
        return bookService.add(request);
    }

    @PutMapping("/books/{id}")
    public InternalBook update(@RequestBody AddInternalBookRequestDTO internalBook, @PathVariable Integer id) {
        internalBook.getInternalBook().setId(id);
        return bookService.update(internalBook);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
    }
}