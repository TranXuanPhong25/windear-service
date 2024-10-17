package com.windear.app.service;

import com.windear.app.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
   public List<Book> findAll() ;
   public Book save(Book book) ;
}
