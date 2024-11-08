package com.windear.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookId {
    @Column(name = "id")
    private String id;

    @Column(name = "borrower_id")
    private Integer borrowerId;

    public BookId() {}

    public BookId(String id, Integer borrowerId) {
        this.id = id;
        this.borrowerId = borrowerId;
    }

    // Getters, setters, equals, and hashCode methods

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Integer borrowerId) {
        this.borrowerId = borrowerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return id == bookId.id && Objects.equals(borrowerId, bookId.borrowerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, borrowerId);
    }
}