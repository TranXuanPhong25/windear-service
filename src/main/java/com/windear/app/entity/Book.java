package com.windear.app.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@MappedSuperclass
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
   @SequenceGenerator(name = "book_seq", sequenceName = "book_id_seq", initialValue = 1000000000, allocationSize = 1)
   protected Integer id;
   protected String title;
   protected String author;
   protected LocalDate releaseDate;
   protected double rating;
   @Column(name = "image_url", nullable = true,columnDefinition = "TEXT")
   protected String imageUrl;

   public Book() {}

   public Book(Integer bookId, String title, String author, LocalDate releaseDate, double rating, String imageUrl) {
      this.id = bookId;
      this.title = title;
      this.author = author;
      this.releaseDate = releaseDate;
      this.rating = rating;
      this.imageUrl = imageUrl;
   }

   public Integer getBookId() {
      return id;
   }

   public void setBookId(Integer id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getAuthorName() {
      return author;
   }

   public void setAuthorName(String authorName) {
      this.author = authorName;
   }

   public LocalDate getReleaseDate() {
      return releaseDate;
   }

   public void setReleaseDate(LocalDate releaseDate) {
      this.releaseDate = releaseDate;
   }

   public double getRating() {
      return rating;
   }

   public void setRating(double rating) {
      this.rating = rating;
   }
}