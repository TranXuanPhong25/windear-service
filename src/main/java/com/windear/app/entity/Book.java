package com.windear.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

@Entity
@Table(name = "book", schema = "book")
public class Book {
   @Id
   @Column(name = "id")
   private Long id;
   
   @Column(name = "title")
   private String title;
   
   @Column(name = "author")
   private String author;
   
   @Column(name = "borrower_id")
   private Integer borrowerId;
   
   @Column(name = "borrow_date")
   private LocalDate borrowDate;
   
   public Book() {
      borrowerId = null;
      borrowDate = null;
   }
   
   public Long getId() {
      return id;
   }
   
   public void setId(Long id) {
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