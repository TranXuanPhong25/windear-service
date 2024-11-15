package com.windear.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;


@MappedSuperclass
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer bookId;
   private String title;
   private Integer authorId;
   private LocalDate releaseDate;
   private double rating;

   public Book() {}

   public Book(Integer bookId, String title, Integer authorId, LocalDate releaseDate, double rating) {
      this.bookId = bookId;
      this.title = title;
      this.authorId = authorId;
      this.releaseDate = releaseDate;
      this.rating = rating;
   }

   public Integer getBookId() {
      return bookId;
   }

   public void setBookId(Integer bookId) {
      this.bookId = bookId;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public Integer getAuthorId() {
      return authorId;
   }

   public void setAuthorId(Integer authorId) {
      this.authorId = authorId;
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