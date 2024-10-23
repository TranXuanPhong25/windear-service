package com.windear.app.service;

import com.windear.app.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
   List<Book> findAll() ;
   Book findById(int id);
   Book add(Book book);
   Book update(Book book);
   void delete(int id);
}
