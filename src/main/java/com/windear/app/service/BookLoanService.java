package com.windear.app.service;

import com.windear.app.dto.SubscribeRequest;
import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;

import java.util.List;

public interface BookLoanService {
    BookLoan findById(BookLoanId id);
    List<BookLoan> findAll();
    List<BookLoan> findAllByUserId(String userId);
    List<BookLoan> findAllByBookId(Integer bookId);
    List<BookLoan> findAllByUserIdAndBookId(String userId, Integer bookId);
    List<BookLoan> findAllBorrowRequest();
    List<BookLoan> findAllActiveBookLoan();
    List<BookLoan> getBorrowRequestByUserId(String userId);
    List<BookLoan> getBorrowedBookByUserId(String userId);
    List<BookLoan> getReturnedBookByUserId(String userId);
    List<BookLoan> getSubscribeRequestOfBook(Integer bookId);
    BookLoan getBorrowRequestByUserIdAndBookId(String userId, Integer bookId);
    void deleteSubscribeRequestOfBook(Integer bookId);
    BookLoan add(BookLoan bookLoan);
    BookLoan sendBorrowRequest(BookLoan bookLoan);
    BookLoan acceptBorrowRequest(BookLoanId bookLoanId);
    BookLoan declineBorrowRequest(BookLoanId bookLoanId);
    BookLoan subscribeToBook(SubscribeRequest request);
    BookLoan returnBook(BookLoanId bookLoanId);
    Integer getAvailableCopiesOfBook(Integer bookId);
}
