package com.windear.app.dto;

import com.windear.app.entity.InternalBook;
import jakarta.persistence.Column;

import java.time.LocalDate;

public class AddInternalBookRequestDTO {
    private InternalBook internalBook;
    private String genres;

    public AddInternalBookRequestDTO() {}

    public AddInternalBookRequestDTO(InternalBook internalBook, String genres) {
        this.internalBook = internalBook;
        this.genres = genres;
    }

    public InternalBook getInternalBook() {
        return internalBook;
    }

    public void setInternalBook(InternalBook internalBook) {
        this.internalBook = internalBook;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }
}
