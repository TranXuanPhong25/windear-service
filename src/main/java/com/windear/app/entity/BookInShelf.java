package com.windear.app.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class BookInShelf extends Book {
    private String imageUrl;
    private LocalDate addedDate;
    private LocalDate readDate;
    private int bookStatus;
    //0 : want to read

    public BookInShelf() {
        super();
    }

    public BookInShelf(Integer id, String title, String author, double rating, String imageUrl, LocalDate releaseDate, LocalDate addedDate, LocalDate readDate, int bookStatus) {
        super(id, title, author,releaseDate, rating);
        this.imageUrl = imageUrl;
        this.addedDate = addedDate;
        this.readDate = readDate;
        this.bookStatus = bookStatus;
    }

    public BookInShelf(Integer id, String title, String author,LocalDate releaseDate, double rating) {
        super(id, title, author, releaseDate,rating);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public int getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(int bookStatus) {
        this.bookStatus = bookStatus;
    }

    public LocalDate getReadDate() {
        return readDate;
    }

    public void setReadDate(LocalDate readDate) {
        this.readDate = readDate;
    }
}
