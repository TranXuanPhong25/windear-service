package com.windear.app.service;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookLoanService {
    BookLoan findById(BookLoanId id);
    List<BookLoan> findAll();
    List<BookLoan> findAllByUserId(String userId);
    List<BookLoan> findAllByBookId(Integer bookId);
    List<BookLoan> findAllByUserIdAndBookId(String userId, Integer bookId);
    List<BookLoan> findAllBorrowRequest();
    List<BookLoan> getBorrowRequestByUserId(String userId);
    List<BookLoan> getBorrowedBookByUserId(String userId);
    List<BookLoan> getReturnedBookByUserId(String userId);
    BookLoan add(BookLoan bookLoan);
    BookLoan borrowBook(BookLoan book);
    BookLoan acceptBorrowRequest(BookLoanId bookLoanId);
    void declineBorrowRequest(BookLoanId bookLoanId);
    BookLoan returnBook(BookLoanId bookLoanId);
}
