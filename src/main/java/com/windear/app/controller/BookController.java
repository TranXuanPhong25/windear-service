package com.windear.app.controller;

import com.windear.app.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class BookController {
   @GetMapping("/books")
   public List<Book> getBooks() {
      return Arrays.asList(
            new Book(
                  1,
                  "Lap trinh can ban",
                  "Phong",
                  "khong biet",
                  "69"
            )
            
      );
   }
   
   // Có thể mapping thêm các endpoint khác nữa...
}