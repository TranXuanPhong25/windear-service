package com.windear.app.service;

import com.windear.app.entity.Book;
import com.windear.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
   public List<Book> findAll() ;
   public Book save(Book book) ;
}
