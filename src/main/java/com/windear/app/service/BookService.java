package com.windear.app.service;

import com.windear.app.entity.Book;
import com.windear.app.entity.BookId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
   List<Book> findAll() ;
   List<Book> findById(String id);
   Book add(Book book);
   void delete(BookId id);
}
