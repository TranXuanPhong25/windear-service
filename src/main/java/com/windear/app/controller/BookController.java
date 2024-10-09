package com.windear.app.controller;

import com.windear.app.entity.Book;
import jakarta.persistence.EntityManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private EntityManager entityManager;
    @GetMapping("/books")
    public String getBooks() {
        return "";
    }

//   @GetMapping("/api/v1/book/borrowed")
//   public List<BookModel> getUserList() {
//        //TODO : implement this
//   }

//    @GetMapping("/api/v1/book/search")
    //params :id
    // Có thể mapping thêm các endpoint khác nữa...
}