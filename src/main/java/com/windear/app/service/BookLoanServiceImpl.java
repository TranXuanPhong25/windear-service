package com.windear.app.service;

import com.windear.app.entity.BookLoan;
import com.windear.app.exception.BookLoanNotFoundException;
import com.windear.app.exception.BorrowSameBookException;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<BookLoan> findAllBorrowRequest() {
        return bookLoanRepository.findAll().stream()
                .filter(BookLoan::isPending)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookLoan> findAllActiveBookLoan() {
        return findAll().stream()
                .filter(bookLoan -> !bookLoan.isPending() && bookLoan.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookLoan> getBorrowRequestByUserId(String userId) {
        return bookLoanRepository.findBorrowRequestByUserId(userId);
    }

    @Override
    public List<BookLoan> getBorrowedBookByUserId(String userId) {
        return bookLoanRepository.findBorrowedBookByUserId(userId);
    }

    @Override
    public List<BookLoan> getReturnedBookByUserId(String userId) {
        return bookLoanRepository.findReturnedBookByUserId(userId);
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
    public BookLoan returnBook(BookLoanId bookLoanId) {
        Optional<BookLoan> bookLoan = bookLoanRepository.findById(bookLoanId);
        if (bookLoan.isPresent()) {
            BookLoan loan = bookLoan.get();
            loan.setReturnDate(LocalDate.now());
            return add(loan);
        } else {
            throw new BookLoanNotFoundException("Bookloan with id not found: "
                    + "BookId: " + bookLoanId.getBookId() + " "
                    + "UserId: " + bookLoanId.getUserId());
        }
    }

    @Override
    @Transactional
    public BookLoan borrowBook(BookLoan bookLoan) {
        List<BookLoan> bookLoans = bookLoanRepository.findByUserIdAndBookID(
                bookLoan.getBookLoanId().getUserId(), bookLoan.getBookLoanId().getBookId());
        for (BookLoan otherBookLoan : bookLoans) {
            if (otherBookLoan.getReturnDate() == null) {
                throw new BorrowSameBookException("User with id: "
                        + otherBookLoan.getBookLoanId().getUserId()
                        + " has already sent a borrow request with book id: "
                        + otherBookLoan.getBookLoanId().getBookId());
            }
        }
        bookLoan.setPending(true);
        bookLoan.getBookLoanId().setBorrowDate(LocalDate.now());
        return add(bookLoan);
    }

    @Override
    @Transactional
    public BookLoan acceptBorrowRequest(BookLoanId bookLoanId) {
        BookLoan bookLoan = findById(bookLoanId);
        bookLoan.setPending(false);
        return add(bookLoan);
    }
}