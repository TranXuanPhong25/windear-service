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
    private Integer borrowTime;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "rating")
    private Double rating;

    public BookLoan() {}

    public BookLoan(BookLoanId bookLoanId, int borrowTime, LocalDate borrowDate, LocalDate returnDate, double rating) {
        this.bookLoanId = bookLoanId;
        this.borrowTime = borrowTime;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.rating = rating;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}