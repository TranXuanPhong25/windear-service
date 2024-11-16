package com.windear.app.entity;

import com.windear.app.primarykey.BookLoanId;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_loan", schema = "public")
public class BookLoan {
    @EmbeddedId
    private BookLoanId bookLoanId;

    @Column(name = "borrow_time")
    private int borrowTime;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public BookLoan() {}

    public BookLoan(BookLoanId bookLoanId, int borrowTime, LocalDate borrowDate, LocalDate returnDate) {
        this.bookLoanId = bookLoanId;
        this.borrowTime = borrowTime;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public BookLoanId getBookLoanId() {
        return bookLoanId;
    }

    public void setBookLoanId(BookLoanId bookLoanId) {
        this.bookLoanId = bookLoanId;
    }

    public Integer getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(Integer borrowTime) {
        this.borrowTime = borrowTime;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}