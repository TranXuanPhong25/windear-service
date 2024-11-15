package com.windear.app.service;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BookLoanService {
    BookLoan findById(BookLoanId id);
    List<BookLoan> findAll();
    List<BookLoan> findAllByUserId(Integer UserId);
    List<BookLoan> findAllByBookId(Integer BookId);
    BookLoan add(BookLoan bookLoan);
    void delete(BookLoanId bookLoanId);
    BookLoan update(BookLoan bookLoan);

}
