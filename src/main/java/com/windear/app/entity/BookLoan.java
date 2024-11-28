package com.windear.app.entity;

import com.windear.app.enums.Status;
import com.windear.app.primarykey.BookLoanId;
import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Timestamp;
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

    @Column(name = "return_date", columnDefinition = "bigint")
    private Long returnDate;

    @Column(name = "borrow_date", columnDefinition = "bigint")
    private Long borrowDate;

    @Column(name = "title")
    private String title;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public BookLoan() {}

    public BookLoan(BookLoanId bookLoanId, Integer borrowTime,
                    Long returnDate, Long borrowDate, String title, String authorName, Status status) {
        this.bookLoanId = bookLoanId;
        this.borrowTime = borrowTime;
        this.returnDate = returnDate;
        this.title = title;
        this.authorName = authorName;
        this.status = status;
        this.borrowDate = borrowDate;
    }

    public BookLoan(BookLoanId bookLoanId, String title, String authorName, Status status) {
        this.bookLoanId = bookLoanId;
        this.title = title;
        this.authorName = authorName;
        this.status = status;
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

    public Long getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Long returnDate) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Long borrowDate) {
        this.borrowDate = borrowDate;
    }
}