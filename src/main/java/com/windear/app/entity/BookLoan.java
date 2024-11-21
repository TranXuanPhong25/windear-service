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

    @Column(name = "return_date")
    private LocalDate returnDate;

    @Column(name = "title")
    private String title;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "is_pending")
    private boolean isPending;

    public BookLoan() {}

    public BookLoan(BookLoanId bookLoanId, Integer borrowTime,
                    LocalDate returnDate, String title, String authorName, boolean isPending) {
        this.bookLoanId = bookLoanId;
        this.borrowTime = borrowTime;
        this.returnDate = returnDate;
        this.title = title;
        this.authorName = authorName;
        this.isPending = isPending;
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }
}