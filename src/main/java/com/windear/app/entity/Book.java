package com.windear.app.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "book", schema = "public")
public class Book {
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   @Column(name = "title")
   private String title;

   @Column(name = "author")
   private String author;

   @Column(name = "is_available")
   private boolean isAvailable;

   @Column(name = "borrower_id")
   private Integer borrowerId;

   @Column(name = "borrow_date")
   private LocalDate borrowDate;

   public Book() {
      isAvailable = true;
      borrowerId = null;
      borrowDate = null;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getTitle() {
      return title;
   }
   
   public String getAuthor() {
      return author;
   }
   
   public void setAuthor(String author) {
      this.author = author;
   }

   public boolean isAvailable() {
      return isAvailable;
   }

   public void setAvailable(boolean available) {
      isAvailable = available;
   }

   public Integer getBorrowerId() {
      return borrowerId;
   }

   public void setBorrowerId(Integer borrowerId) {
      this.borrowerId = borrowerId;
   }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }
}