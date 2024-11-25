package com.windear.app.controller;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.service.BookLoanService;
import com.windear.app.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookloan")
public class BookLoanController {
    private final BookLoanService bookLoanService;
    private final NotificationService notificationService;

    @Autowired
    public BookLoanController(BookLoanService bookLoanService, NotificationService notificationService) {
        this.bookLoanService = bookLoanService;
        this.notificationService = notificationService;
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


    @PostMapping()
    public BookLoan declineBorrowRequest(@RequestBody BookLoanId loanId) {
        BookLoan bookLoan = bookLoanService.declineBorrowRequest(loanId);
        notificationService.sendNotification(loanId.getUserId(), "Your borrow request has been declined.");
        return bookLoan;
    }

    @PostMapping("/borrow")
    public BookLoan sendBorrowRequest(@RequestBody BookLoan bookLoan) {
        BookLoan borrowRequest = bookLoanService.borrowBook(bookLoan);
        notificationService.sendNotification(bookLoan.getBookLoanId().getUserId(), "Your borrow request has been received.");
        return borrowRequest;
    }

    @PutMapping("/borrow")
    public BookLoan acceptBorrowRequest(@RequestBody BookLoanId loanId) {
        BookLoan borrowRequest = bookLoanService.acceptBorrowRequest(loanId);
        notificationService.sendNotification(loanId.getUserId(), "Your borrow request has been accepted.");
        return borrowRequest;
    }

    @PutMapping("/return")
    public BookLoan returnBook(@RequestBody BookLoanId loanId) {
        return bookLoanService.returnBook(loanId);
    }

}
