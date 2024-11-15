package com.windear.app.controller;

import com.windear.app.entity.BookGenre;
import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookGenreId;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookGenreController {
    private final BookGenreService bookGenreService;

    @Autowired
    public BookGenreController(BookGenreService bookGenreService) {
        this.bookGenreService = bookGenreService;
    }

    @PostMapping("/bookgenre")
    public BookGenre add(@RequestBody BookGenre bookGenre) {
        return bookGenreService.add(bookGenre);
    }

    @GetMapping("/bookgenre/{bookId}/{genreId}")
    public BookGenre findById(@PathVariable Integer bookId, @PathVariable Integer genreId) {
        BookGenreId bookGenreId = new BookGenreId(bookId, genreId);
        return bookGenreService.findById(bookGenreId);
    }

    @GetMapping("/bookgenre/genre/{genreId}")
    public List<BookGenre> findAllByGenreId(@PathVariable Integer genreId) {
        return bookGenreService.findAllByGenreId(genreId);
    }

    @GetMapping("/bookgenre/book/{bookId}")
    public List<BookGenre> findAllByBookId(@PathVariable Integer bookId) {
        return bookGenreService.findAllByBookId(bookId);
    }

    @DeleteMapping("/bookgenre/{bookId}/{genreId}")
    public void delete(@PathVariable Integer bookId, @PathVariable Integer genreId) {
        BookGenreId bookGenreId = new BookGenreId(bookId, genreId);
        bookGenreService.delete(bookGenreId);
    }



}
