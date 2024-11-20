package com.windear.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "internal_book", schema = "public")
public class InternalBook extends Book {
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "isbn10")
    private String isbn10;

    @Column(name = "isbn13")
    private String isbn13;

    @Column(name = "author_image_url", columnDefinition = "TEXT")
    private String authorImageUrl;

    @Column(name = "author_description", columnDefinition = "TEXT")
    private String authorDescription;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "format")
    private String format;

    @Column(name = "language")
    private String language;

    @Column(name = "pages")
    private Integer pages;

    public InternalBook() {}

    public InternalBook(Integer id, String title, String author, LocalDate releaseDate, double rating, String imageUrl, String description, String isbn10, String isbn13, String authorImageUrl, String authorDescription, String publisher, String format, String language, Integer pages) {
        super(id, title, author, releaseDate, rating, imageUrl);
        this.description = description;
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.authorImageUrl = authorImageUrl;
        this.authorDescription = authorDescription;
        this.publisher = publisher;
        this.format = format;
        this.language = language;
        this.pages = pages;
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

    public String getAuthorImageUrl() {
        return authorImageUrl;
    }

    public void setAuthorImageUrl(String authorImageUrl) {
        this.authorImageUrl = authorImageUrl;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
