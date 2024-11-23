package com.windear.app.controller;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.service.BookLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookloan")
public class BookLoanController {
    private final BookLoanService bookLoanService;

    @Autowired
    public BookLoanController(BookLoanService bookLoanService) {
        this.bookLoanService = bookLoanService;
    }

    @GetMapping()
    public List<BookLoan> getBookLoanList() {
        return bookLoanService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<BookLoan> findAllByUserId(@PathVariable String userId) {
        return bookLoanService.findAllByUserId(userId);
    }

    @GetMapping("/book/{bookId}")
    public List<BookLoan> findAllByBookId(@PathVariable Integer bookId) {
        return bookLoanService.findAllByBookId(bookId);
    }

    @GetMapping("/{bookId}/{userId}")
    public List<BookLoan> findByUserIdAndBookId(@PathVariable Integer bookId, @PathVariable String userId) {
        return bookLoanService.findAllByUserIdAndBookId(userId, bookId);
    }

    @GetMapping("/request")
    public List<BookLoan> getAllRequest() {
        return bookLoanService.findAllBorrowRequest();
    }

    @GetMapping("/request/{userId}")
    public List<BookLoan> getBorrowRequestByUserId(@PathVariable String userId) {
        return bookLoanService.getBorrowRequestByUserId(userId);
    }

    @GetMapping("/borrow/{userId}")
    public List<BookLoan> getBorrowedBookByUserId(@PathVariable String userId) {
        return bookLoanService.getBorrowedBookByUserId(userId);
    }

    @GetMapping("/return/{userId}")
    public List<BookLoan> getReturnedBookByUserId(@PathVariable String userId) {
        return bookLoanService.getReturnedBookByUserId(userId);
    }


    @DeleteMapping()
    public void declineBorrowRequest(@RequestBody BookLoanId loanId) {
        bookLoanService.declineBorrowRequest(loanId);
    }

    @PostMapping("/borrow")
    public BookLoan sendBorrowRequest(@RequestBody BookLoan bookLoan) {
        return bookLoanService.borrowBook(bookLoan);
    }

    @PutMapping("/borrow")
    public BookLoan acceptBorrowRequest(@RequestBody BookLoanId loanId) {
        return bookLoanService.acceptBorrowRequest(loanId);
    }

    @PutMapping("/return")
    public BookLoan returnBook(@RequestBody BookLoanId loanId) {
        return bookLoanService.returnBook(loanId);
    }

}
