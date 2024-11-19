package com.windear.app.primarykey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class BookLoanId {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "book_id")
    private Integer bookId;

    public BookLoanId() {}

    public BookLoanId(String userId, Integer bookId) {
        this.userId = userId;
        this.bookId = bookId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoanId other = (BookLoanId) o;
        return bookId == other.bookId && userId == other.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bookId);
    }
}
