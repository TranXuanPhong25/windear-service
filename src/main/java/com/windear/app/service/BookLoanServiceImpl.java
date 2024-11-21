package com.windear.app.service;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookLoanServiceImpl implements BookLoanService {
    private final BookLoanRepository bookLoanRepository;

    @Autowired
    public BookLoanServiceImpl(BookLoanRepository bookLoanRepository) {
        this.bookLoanRepository = bookLoanRepository;
    }

    @Override
    public List<BookLoan> findAll() {
        return bookLoanRepository.findAll();
    }

    @Override
    public List<BookLoan> findAllByUserId(String userId) {
        return bookLoanRepository.findAllByUserId(userId);
    }

    @Override
    public List<BookLoan> findAllByBookId(Integer bookId) {
        return bookLoanRepository.findAllByBookId(bookId);
    }

    @Override
    public List<BookLoan> findAllByUserIdAndBookId(String userId, Integer bookId) {
        return bookLoanRepository.findByUserIdAndBookID(userId, bookId);
    }

    @Override
    @Transactional
    public BookLoan add(BookLoan bookLoan) {
        return bookLoanRepository.save(bookLoan);
    }

    @Override
    public BookLoan findById(BookLoanId id) {
        Optional<BookLoan> bookLoan = bookLoanRepository.findById(id);
        if (bookLoan.isPresent()) {
            return bookLoan.get();
        } else {
            throw new RuntimeException("Bookloan with id not found: " + id.getBookId() + " " + id.getUserId());
        }
    }

    @Override
    @Transactional
    public void declineBorrowRequest(BookLoanId id) {
        Optional<BookLoan> bookLoan = bookLoanRepository.findById(id);
        if (bookLoan.isPresent()) {
            bookLoanRepository.deleteById(id);
        } else {
            throw new RuntimeException("bookloan with id not found: "
                    + "BookId: " + id.getBookId() + " "
                    + "UserId: " + id.getUserId());
        }
    }

    @Override
    @Transactional
    public BookLoan borrowBook(BookLoan book) {
        book.setPending(true);
        book.getBookLoanId().setBorrowDate(LocalDate.now());
        return add(book);
    }

    @Override
    @Transactional
    public BookLoan acceptBorrowRequest(BookLoanId bookLoanId) {
        BookLoan bookLoan = findById(bookLoanId);
        bookLoan.setPending(false);
        return add(bookLoan);
    }
}