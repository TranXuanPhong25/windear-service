package com.windear.app.entity;

import java.time.LocalDate;


@MappedSuperclass
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   protected Integer bookId;
   protected String title;
   protected String authorId;
   protected LocalDate releaseDate;
   protected double rating;

   public Book() {}

   public Book(Integer bookId, String title, String authorId, LocalDate releaseDate, double rating) {
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

   public String getAuthorId() {
      return authorId;
   }

   public void setAuthorId(String authorId) {
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