package com.windear.app.controller;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.Book;
import com.windear.app.entity.BookGenre;


import com.windear.app.entity.Genre;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.InternalBook;
import com.windear.app.primarykey.BookGenreId;
import com.windear.app.service.BookGenreService;
import com.windear.app.service.GenreService;
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
    private final GenreService genreService;

    @Autowired
    public InternalBookController(InternalBookService bookService, BookGenreService bookGenreService, GenreService genreService) {
        this.bookService = bookService;
        this.bookGenreService = bookGenreService;
        this.genreService = genreService;
    }

    @GetMapping("/books")
    public List<InternalBookDTO> findAll() {
        List<InternalBook> books = bookService.findAll();
        List<InternalBookDTO> booksDto = new ArrayList<>();
        for(InternalBook it : books) {
            booksDto.add(bookService.convertToDTO(it));
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
                genres += genreService.findById(bookGenres.get(i).getBookGenreId().getGenreId()).getName() + ',';
            } else {
                genres += genreService.findById(bookGenres.get(i).getBookGenreId().getGenreId()).getName();
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

    @PutMapping("/books/{id}")
    public InternalBook update(@RequestBody AddInternalBookRequestDTO internalBook, @PathVariable Integer id) {
        internalBook.getInternalBook().setId(id);

        return bookService.update(internalBook.getInternalBook());
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
    }

}