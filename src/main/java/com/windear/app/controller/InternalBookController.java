package com.windear.app.controller;

import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.InternalBook;
import com.windear.app.service.InternalBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/db")
public class InternalBookController {
    private final InternalBookService bookService;

    @Autowired
    public InternalBookController(InternalBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<InternalBookDTO> findAll() {
        List<InternalBook> books = bookService.findAll();
        List<InternalBookDTO> booksDTO = new ArrayList<>();
        for(InternalBook it : books) {
            booksDTO.add(convertToDTO(it));
        }
        return booksDTO;
    }

    @GetMapping("/books/{id}")
    public InternalBook findBookById(@PathVariable Integer id) {
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
    public InternalBook addBook(@RequestBody InternalBook book) {
        return bookService.add(book);
    }

    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Integer id) {
        bookService.delete(id);
    }

    private InternalBookDTO convertToDTO(InternalBook book) {
        InternalBookDTO dto = new InternalBookDTO();
        dto.setId(book.getBookId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setAddDate(book.getAddDate());
        dto.setReleaseDate(book.getReleaseDate());
        return dto;
    }
}