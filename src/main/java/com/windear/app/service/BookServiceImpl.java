package com.windear.app.service;

import com.windear.app.entity.Book;
import com.windear.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
   
   @Autowired
   private BookRepository bookRepository;
   
   public List<Book> findAll() {
      return bookRepository.findAll();
   }
   
   public Book save(Book book) {
      return bookRepository.save(book);
   }
}
