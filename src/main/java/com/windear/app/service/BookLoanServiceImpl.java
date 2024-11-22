package com.windear.app.service;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<BookLoan> findAllByUserId(Integer userId) {
        return bookLoanRepository.findAllByUserId(userId);
    }

    @Override
    public List<BookLoan> findAllByBookId(Integer bookId) {
        return bookLoanRepository.findAllByBookId(bookId);
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
            throw new RuntimeException("bookloan with id not found: " + id.getBookId() + " " + id.getUserId());
        }
    }

    @Override
    @Transactional
    public void delete(BookLoanId id) {
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
    public BookLoan update(BookLoan bookLoan) {
        BookLoan bookLoanFromDB = findById(bookLoan.getBookLoanId());
        if (bookLoan.getBorrowTime() != null) {
            bookLoanFromDB.setBorrowTime(bookLoan.getBorrowTime());
        }
        if (bookLoan.getBorrowDate() != null) {
            bookLoanFromDB.setBorrowDate(bookLoan.getBorrowDate());
        }
        if (bookLoan.getReturnDate() != null) {
            bookLoanFromDB.setReturnDate(bookLoan.getReturnDate());
        }
        if (bookLoan.getTitle() != null) {
            bookLoanFromDB.setTitle(bookLoan.getTitle());
        }
        if (bookLoan.getAuthorName() != null) {
            bookLoanFromDB.setAuthorName(bookLoan.getAuthorName());
        }
        bookLoanRepository.save(bookLoanFromDB);
        return bookLoanFromDB;
    }
    /*
    @Override
    @Transactional
    public String borrowBook(int userId, String bookId) {
        BookLoan user = findById(userId);
        String externalBook = externalBookService.findById(bookId);
        Book book = new Book();
        book.setBookId(new BookId(bookId, userId));
        book.setBorrowDate(LocalDate.now());
        bookService.add(book);
        return externalBook;
    }

    @Override
    @Transactional
    public String returnBook(int userId, String bookId) {
        BookLoan user = findById(userId);
        String externalBook = externalBookService.findById(bookId);
        for (Book book : user.getBorrowedBooks()) {
            if (book.getId().equals(bookId)) {
                book.setReturnDate(LocalDate.now());
                return externalBook;
            }
        }
        throw new BookNotFoundException("Book with id not found: " + bookId);
    }*/
}