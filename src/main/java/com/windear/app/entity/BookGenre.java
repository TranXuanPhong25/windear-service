package com.windear.app.entity;

import com.windear.app.primarykey.BookGenreId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "book_genre", schema = "public")
public class BookGenre {
    @EmbeddedId
    private BookGenreId bookGenreId;

    public BookGenre() {}

    public BookGenre(BookGenreId bookGenreId) {
        this.bookGenreId = bookGenreId;
    }

    public BookGenreId getBookGenreId() {
        return bookGenreId;
    }

    public void setBookGenreId(BookGenreId bookGenreId) {
        this.bookGenreId = bookGenreId;
    }
}
