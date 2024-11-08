package com.windear.app.service;

import com.windear.app.entity.Book;
import com.windear.app.entity.BookId;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
   private final BookRepository bookRepository;

   @Autowired
   public BookServiceImpl(BookRepository bookRepository) {
      this.bookRepository = bookRepository;
   }

   @Override
   public List<Book> findAll() {
      return bookRepository.findAll();
   }

   @Override
   public List<Book> findById(String id) {
      List<Book> book = bookRepository.findById(id);
      if (book.size() == 0) {
         throw new BookNotFoundException("Book with id not found: " + id);
      }
      return book;
   }

   @Override
   @Transactional
   public Book add(Book book) {
      return bookRepository.save(book);
   }

   @Override
   @Transactional
   public void delete(BookId id) {
      bookRepository.deleteById(id);
   }

//   public Book convertToBook(ExternalBook externalBook) {
//      Book book = new Book();
//
//   }
}
