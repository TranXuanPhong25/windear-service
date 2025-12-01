package com.windear.app.service;

import com.windear.app.entity.BookLoan;
import com.windear.app.enums.Status;
import com.windear.app.exception.BookNotAvailableException;
import com.windear.app.exception.BorrowSameBookException;
import com.windear.app.primarykey.BookLoanId;
import com.windear.app.repository.BookLoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookLoanBoundaryTest {

    @Mock
    private BookLoanRepository bookLoanRepository;

    @Mock
    private BookCopyService bookCopyService;

    @InjectMocks
    private BookLoanServiceImpl bookLoanService;

    @Test
    void sendBorrowRequest_ShouldAccept_WhenBorrowTimeIsLessThan1Day() {
        // Currently validated only at frontend
        BookLoanId newId = new BookLoanId("user2", 1, System.currentTimeMillis());
        BookLoan newLoan = new BookLoan();
        newLoan.setBookLoanId(newId);
        newLoan.setTitle("Book");
        newLoan.setBorrowTime(0);

        when(bookCopyService.getQuantityOfBookCopy(1)).thenReturn(1);
        when(bookLoanRepository.findByUserIdAndBookID("user2", 1)).thenReturn(Collections.emptyList());
        when(bookLoanRepository.save(any(BookLoan.class))).thenReturn(newLoan);

        assertThrows(IllegalArgumentException.class, () -> {
            bookLoanService.sendBorrowRequest(newLoan);
        });

        // Negative borrow time
        newLoan.setBorrowTime(-5);

        when(bookCopyService.getQuantityOfBookCopy(1)).thenReturn(1);
        when(bookLoanRepository.findByUserIdAndBookID("user3", 1)).thenReturn(Collections.emptyList());
        when(bookLoanRepository.save(any(BookLoan.class))).thenReturn(newLoan);

        // BUG: Should throw IllegalArgumentException but doesn't
        assertThrows(IllegalArgumentException.class, () -> {
            bookLoanService.sendBorrowRequest(newLoan);
        });
    }


    @Test
    void sendBorrowRequest_ShouldAccept_WhenBorrowTimeIsExtremelyLarge() {
        BookLoanId newId = new BookLoanId("user4", 1, System.currentTimeMillis());
        BookLoan newLoan = new BookLoan();
        newLoan.setBookLoanId(newId);
        newLoan.setTitle("Book");
        newLoan.setBorrowTime(Integer.MAX_VALUE); // BOUNDARY: max int

        when(bookCopyService.getQuantityOfBookCopy(1)).thenReturn(1);
        when(bookLoanRepository.findByUserIdAndBookID("user4", 1)).thenReturn(Collections.emptyList());
        when(bookLoanRepository.save(any(BookLoan.class))).thenReturn(newLoan);

        assertThrows(IllegalArgumentException.class, () -> {
            bookLoanService.sendBorrowRequest(newLoan);
        }, "BorrowTime must not exceed 365 days");
    }

    @Test
    void sendBorrowRequest_ShouldThrowException_WhenQuantityIsZero() {
        when(bookCopyService.getQuantityOfBookCopy(1)).thenReturn(0);

        BookLoanId newId = new BookLoanId("user5", 1, System.currentTimeMillis());
        BookLoan newLoan = new BookLoan();
        newLoan.setBookLoanId(newId);
        newLoan.setTitle("Book");
        newLoan.setBorrowTime(7);

        assertThrows(BookNotAvailableException.class, () -> {
            bookLoanService.sendBorrowRequest(newLoan);
        });
    }

    @Test
    void acceptBorrowRequest_ShouldSetBorrowDate_WhenBorrowDateRequestIsNull() {
        BookLoan bookLoan = new BookLoan();
        BookLoanId bookLoanId = new BookLoanId("user7", 1, System.currentTimeMillis() - 1000);
        bookLoan.setBookLoanId(bookLoanId);
        bookLoan.setStatus(Status.PENDING);
        bookLoan.setBorrowDate(null);
        
        when(bookLoanRepository.findById(bookLoanId)).thenReturn(Optional.of(bookLoan));
        when(bookLoanRepository.save(any(BookLoan.class))).thenReturn(bookLoan);

        bookLoanService.acceptBorrowRequest(bookLoanId);

        assertNotNull(bookLoan.getBorrowDate());
        assertEquals(Status.ACCEPT, bookLoan.getStatus());
    }

}
