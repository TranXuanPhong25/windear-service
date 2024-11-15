package com.windear.app.primarykey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class BookGenreId {
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "genre_id")
    private Integer genreId;

    public BookGenreId() {}

    public BookGenreId(Integer bookId, Integer genreId) {
        this.bookId = bookId;
        this.genreId = genreId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookGenreId other = (BookGenreId) o;
        return bookId == other.bookId && genreId == other.genreId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, genreId);
    }
}
