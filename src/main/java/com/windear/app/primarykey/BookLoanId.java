package com.windear.app.primarykey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class BookLoanId {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "request_date")
    private LocalDate requestDate;

    public BookLoanId() {}

    public BookLoanId(String userId, Integer bookId, LocalDate requestDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.requestDate = requestDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate request_date) {
        this.requestDate = request_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoanId other = (BookLoanId) o;
        return Objects.equals(bookId, other.bookId) && Objects.equals(userId, other.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }
}
