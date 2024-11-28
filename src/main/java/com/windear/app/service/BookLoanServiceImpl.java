package com.windear.app.service;

import com.windear.app.dto.SubscribeRequest;
import com.windear.app.entity.BookLoan;
import com.windear.app.enums.Status;
import com.windear.app.exception.BookLoanDeleteException;
import com.windear.app.exception.BookLoanNotFoundException;
import com.windear.app.exception.BookNotAvailableException;
import com.windear.app.exception.BorrowSameBookException;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.repository.BookLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookLoanServiceImpl implements BookLoanService {
    private final BookLoanRepository bookLoanRepository;
    private final BookCopyService bookCopyService;

    @Autowired
    public BookLoanServiceImpl(BookLoanRepository bookLoanRepository,
                               BookCopyService bookCopyService) {
        this.bookLoanRepository = bookLoanRepository;
        this.bookCopyService = bookCopyService;
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
                .filter(bookLoan -> bookLoan.getStatus() == Status.PENDING)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookLoan> findAllActiveBookLoan() {
        return findAll().stream()
                .filter(bookLoan -> bookLoan.getStatus() == Status.ACCEPT && bookLoan.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookLoan> getSubscribeRequestOfBook(Integer bookId) {
        return findAll().stream()
                .filter(bookLoan -> bookLoan.getStatus() == Status.SUBSCRIBE && bookLoan.getBookLoanId().getBookId().equals(bookId))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSubscribeRequestOfBook(Integer bookId) {
        List<BookLoan> subscribeRequests = getSubscribeRequestOfBook(bookId);
        bookLoanRepository.deleteAll(subscribeRequests);
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
            throw new BookLoanNotFoundException("Book Loan with id not found: " + id.getBookId() + " " + id.getUserId());
        }
    }

    @Override
    @Transactional
    public BookLoan declineBorrowRequest(BookLoanId id) {
        BookLoan bookLoan = findById(id);
        bookLoan.setStatus(Status.DECLINE);
        return bookLoan;
    }

    @Override
    @Transactional
    public BookLoan subscribeToBook(SubscribeRequest request) {
        String userId = request.getUserId();
        Integer bookId = request.getBookId();
        BookLoanId bookLoanId = new BookLoanId(userId, bookId);
        BookLoan bookLoan = new BookLoan(bookLoanId, request.getTitle(),
                request.getAuthorName(), Status.SUBSCRIBE);
        return add(bookLoan);
    }

    @Override
    @Transactional
    public BookLoan returnBook(BookLoanId bookLoanId) {
        BookLoan bookLoan = findById(bookLoanId);
        bookLoan.setReturnDate(new Timestamp(System.currentTimeMillis()).getTime());
        if (!bookLoan.getStatus().equals(Status.ACCEPT)) {
            return bookLoan;
        }
        bookLoan.setReturnDate(new Timestamp(System.currentTimeMillis()).getTime());
        bookCopyService.modifyQuantityOfBookCopy(bookLoanId.getBookId(), 1);
        return add(bookLoan);
    }

    @Override
    public Integer getAvailableCopiesOfBook(Integer bookId) {
        return bookCopyService.getQuantityOfBookCopy(bookId);
    }

    @Override
    public void deleteBookLoan(BookLoanId bookLoanId) {
        BookLoan bookLoan = findById(bookLoanId);
        if (bookLoan.getStatus() == Status.ACCEPT && bookLoan.getReturnDate() == null) {
            throw new BookLoanDeleteException("Cannot delete an active book loan that has not been returned.");
        }
        if (bookLoan.getStatus() == Status.PENDING) {
            bookCopyService.modifyQuantityOfBookCopy(bookLoanId.getBookId(), 1);
        }
        bookLoanRepository.deleteById(bookLoanId);
    }

    @Override
    public BookLoan getBorrowRequestByUserIdAndBookId(String userId, Integer bookId) {
        //status == PENDING
        List<BookLoan> requests = getBorrowRequestByUserId(userId);
        for (BookLoan request : requests) {
            if (request.getBookLoanId().getBookId().equals(bookId)) {
                return request;
            }
        }
        //status == ACCEPT and returnDate == null
        requests = getBorrowedBookByUserId(userId);
        for (BookLoan request : requests) {
            if (request.getBookLoanId().getBookId().equals(bookId)) {
                return request;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public BookLoan sendBorrowRequest(BookLoan bookLoan) {
        String userId = bookLoan.getBookLoanId().getUserId();
        Integer bookId = bookLoan.getBookLoanId().getBookId();
        int quantityOfCopies = bookCopyService.getQuantityOfBookCopy(bookId);
        if (quantityOfCopies == 0) {
            throw new BookNotAvailableException("The book with id " + bookId
                    + " is not available for borrowing.");
        }
        List<BookLoan> bookLoans = bookLoanRepository.findByUserIdAndBookID(userId, bookId);
        for (BookLoan otherBookLoan : bookLoans) {
            if (otherBookLoan.getReturnDate() == null) {
                throw new BorrowSameBookException("User with id: "
                        + userId
                        + " has already sent a borrow request with book id: "
                        + bookId);
            }
        }
        bookLoan.setStatus(Status.PENDING);
        bookLoan.setBorrowTime(bookLoan.getBorrowTime());
        bookLoan.getBookLoanId().setRequestDate(new Timestamp(System.currentTimeMillis()).getTime());
        bookCopyService.modifyQuantityOfBookCopy(bookId, -1);
        return add(bookLoan);
    }

    @Override
    @Transactional
    public BookLoan acceptBorrowRequest(BookLoanId bookLoanId) {
        BookLoan bookLoan = findById(bookLoanId);
        bookLoan.setStatus(Status.ACCEPT);
        bookLoan.setBorrowDate(new Timestamp(System.currentTimeMillis()).getTime());
        return add(bookLoan);
    }
}