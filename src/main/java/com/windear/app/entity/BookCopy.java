package com.windear.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_copy", schema = "public")
public class BookCopy {
    @Id
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "quantity")
    private Integer quantity;

    public BookCopy() {

    }

    public BookCopy(Integer bookId, Integer quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
