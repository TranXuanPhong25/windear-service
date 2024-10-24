package com.windear.app.entity;

import java.time.LocalDate;

public class ExternalBook{
    private int id;
    private String title;
    private String authors;
    private Double rating;
    private String imageUrl;

    public ExternalBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Book convertToBook() {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(authors);
        return book;
    }
}
