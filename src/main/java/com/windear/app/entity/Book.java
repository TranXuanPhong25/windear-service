package com.windear.app.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDate;


@MappedSuperclass
public class Book {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
   @SequenceGenerator(name = "book_seq", sequenceName = "book_id_seq", initialValue = 1000000000, allocationSize = 1)
   protected Integer bookId;
   protected String title;
   protected Integer authorId;
   protected LocalDate releaseDate;
   protected double rating;
   protected String imageUrl;

   public Book() {}

   public Book(Integer bookId, String title, Integer authorId, LocalDate releaseDate, double rating, String imageUrl) {
      this.bookId = bookId;
      this.title = title;
      this.authorId = authorId;
      this.releaseDate = releaseDate;
      this.rating = rating;
      this.imageUrl = imageUrl;
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