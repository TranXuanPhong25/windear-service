package com.windear.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "popular_book", schema = "public")
public class PopularBook {
    @Id
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "score")
    private Integer score;

    public PopularBook() {}

    public PopularBook(Integer bookId, Integer score) {
        this.bookId = bookId;
        this.score = score;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
