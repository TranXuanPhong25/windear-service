package com.windear.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import org.springframework.data.annotation.Id;

@Entity
@Table(name= "book", schema="book")
public class Book {
   
   @jakarta.persistence.Id
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   private String title;
   private String author;

   public void setId(Long id) {
      this.id = id;
   }
   
   public Long getId() {
      return id;
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
}