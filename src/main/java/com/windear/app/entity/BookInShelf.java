package com.windear.app.entity;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

public class BookInShelf extends Book {
    private LocalDate addedDate;
    private LocalDate readDate;
    private int bookStatus;
    private double userRating;
    //0 : want to read

    public BookInShelf() {
        super();
    }

    public BookInShelf(Integer id, String title, String author, double rating, String imageUrl, LocalDate releaseDate, LocalDate addedDate, LocalDate readDate, int bookStatus, double userRating) {
        super(id, title, author,releaseDate, rating,imageUrl);
        this.addedDate = addedDate;
        this.readDate = readDate;
        this.bookStatus = bookStatus;
        this.userRating = userRating;
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

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}
