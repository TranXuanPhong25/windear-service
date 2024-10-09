package com.windear.app.controller;

import com.windear.app.model.Book;
import com.windear.app.model.UserModel;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {
    private EntityManager entityManager;
    @GetMapping("/books")
    public String getBooks() {
             return new Book(
                  1,
                  "Lap trinh can ban",
                  "Phong",
                  "khong biet",
                  "69"
            ).toString();
    }

//   @GetMapping("/api/v1/book/borrowed")
//   public List<BookModel> getUserList() {
//        //TODO : implement this
//   }

//    @GetMapping("/api/v1/book/search")
    //params :id
    // Có thể mapping thêm các endpoint khác nữa...
}