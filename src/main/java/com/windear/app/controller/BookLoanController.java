package com.windear.app.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.service.BookLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookLoanController {
    private final BookLoanService bookLoanService;

    @Autowired
    public BookLoanController(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }

    @PostMapping("/bookloan")
    public BookLoan addBookLoan(@RequestBody BookLoan bookLoan) {
        return bookLoanService.add(bookLoan);
    }

    @GetMapping("/bookloan")
    public List<BookLoan> getBookLoanList() {
        return bookLoanService.findAll();
    }

    @GetMapping("/bookloan/user/{userId}")
    public List<BookLoan> findAllByUserId(@PathVariable Integer userId) {
        return bookLoanService.findAllByUserId(userId);
    }

    @GetMapping("bookloan/book/{bookId}")
    public List<BookLoan> findAllByBookId(@PathVariable Integer bookId) {
        return bookLoanService.findAllByBookId(bookId);
    }

    @GetMapping("/bookloan/{bookId}/{userId}")
    public BookLoan findById(@PathVariable Integer bookId, @PathVariable String userId) {
        BookLoanId bookLoanId = new BookLoanId(userId, bookId);
        return bookLoanService.findById(bookLoanId);
    }

    @DeleteMapping("/bookloan/{bookId}/{userId}")
    public void deleteBookLoan(@PathVariable Integer bookId, @PathVariable String userId) {
        BookLoanId bookLoanId = new BookLoanId(userId, bookId);
        bookLoanService.delete(bookLoanId);
    }

    /*
    @PutMapping("/users")
    public BookLoan updateUser(@RequestBody BookLoan user) {
        return userService.update(user);
    }
    /*
    @PostMapping("/users/{userId}/borrow/{bookId}")
    public String borrowBook(@PathVariable int userId, @PathVariable String bookId) {
        return userService.borrowBook(userId, bookId);
    }

    @PostMapping("users/{userId}/return/{bookId}")
    public String returnBook(@PathVariable int userId, @PathVariable String bookId) {
        return userService.returnBook(userId, bookId);
    }*/
}
