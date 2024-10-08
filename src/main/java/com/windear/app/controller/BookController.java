package com.windear.app.controller;

import com.windear.app.entity.Book;
import com.windear.app.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
   
   @Autowired
   private BookService bookService;
   
   @GetMapping
   public List<Book> getAllBooks() {
      return bookService.findAll();
   }
   
//   @PostMapping
//   public Book createBook(@RequestBody Book book) {
//      return bookService.save(book);
//   }
}
