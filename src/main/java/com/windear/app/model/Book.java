package com.windear.app.model;

public class Book {
   private int id;
   private String title;
   private String author;
   private String publisher;
   private String pages;
   public Book() {}
   public Book(int id, String title, String author, String publisher, String pages) {
      this.id = id;
      this.title = title;
      this.author = author;
      this.publisher = publisher;
      this.pages = pages;
   }
   
   public String getTitle() {
      return title;
   }
   
   public void setTitle(String title) {
      this.title = title;
   }
   
   public int getId() {
      return id;
   }
   
   public void setId(int id) {
      this.id = id;
   }
   
   public String getAuthor() {
      return author;
   }
   
   public void setAuthor(String author) {
      this.author = author;
   }
   
   public String getPublisher() {
      return publisher;
   }
   
   public void setPublisher(String publisher) {
      this.publisher = publisher;
   }
   
   public String getPages() {
      return pages;
   }
   
   public void setPages(String pages) {
      this.pages = pages;
   }
}
