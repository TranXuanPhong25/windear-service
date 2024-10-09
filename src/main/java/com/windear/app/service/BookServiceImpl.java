package com.windear.app.service;

import com.windear.app.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Override
   public List<Book> findAll() {
      return List.of();
   }

   @Override
   public Book save(Book book) {
      return null;
   }
}
