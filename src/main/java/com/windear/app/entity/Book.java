package com.windear.app.entity;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "book", schema = "public")
public class Book {
   @EmbeddedId
   private BookId bookId;
   
   @Column(name = "borrow_date")
   private LocalDate borrowDate;

   @Column(name = "return_date")
   private LocalDate returnDate;
   
   public Book() {
      borrowDate = null;
      returnDate = null;
   }
   
   public BookId getBookId() {
      return bookId;
   }

   public void setBookId(BookId bookId) {
      this.bookId = bookId;
   }

   public void setId(String id) {
      bookId.setId(id);
   }

   public String getId() {
      return bookId.getId();
   }

   public void setBorrowerId(int id) {
      bookId.setBorrowerId(id);
   }

   public int getBorrowerId() {
      return bookId.getBorrowerId();
   }
   
   public LocalDate getReturnDate() {
      return returnDate;
   }

   public void setReturnDate(LocalDate returnDate) {
      this.returnDate = returnDate;
   }

   public LocalDate getBorrowDate() {
      return borrowDate;
   }
   
   public void setBorrowDate(LocalDate borrowDate) {
      this.borrowDate = borrowDate;
   }
}