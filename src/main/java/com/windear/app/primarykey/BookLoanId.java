package com.windear.app.primarykey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class BookLoanId {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "request_date", columnDefinition = "bigint")
    private Long requestDate;

    public BookLoanId() {}

    public BookLoanId(String userId, Integer bookId, Long requestDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.requestDate = requestDate;
    }

    public BookLoanId(String userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
        this.requestDate = new Timestamp(System.currentTimeMillis()).getTime();
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

    public Long getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Long requestDate) {
        this.requestDate = requestDate;
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
