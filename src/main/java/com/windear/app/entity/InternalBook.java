package com.windear.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "interal_book", schema = "public")
public class InternalBook extends Book {
    @Column(name = "description")
    private String description;

    @Column(name = "isbn10")
    private String isbn10;

    @Column(name = "isbn13")
    private String isbn13;

    public InternalBook() {}

    public InternalBook(Integer bookId, String title, Integer authorId, LocalDate releaseDate, double rating, String description, String isbn10, String isbn13) {
        super(bookId, title, authorId, releaseDate, rating);
        this.description = description;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }
}
