package com.windear.app.service;

import com.windear.app.entity.Book;
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
   public Book findById(int id) {
      Optional<Book> book = bookRepository.findById(id);
      if (book.isPresent()) {
         return book.get();
      }
      else {
         throw new BookNotFoundException("Book with id not found: " + id);
      }
   }

   @Override
   @Transactional
   public Book add(Book book) {
      return bookRepository.save(book);
   }

   @Override
   @Transactional
   public Book update(Book book) {
      Book bookFromDB = findById(book.getId());
      if (book.getAuthor() != null) {
         bookFromDB.setAuthor(book.getAuthor());
      }
      if (book.getTitle() != null) {
         bookFromDB.setTitle(book.getTitle());
      }
      bookRepository.save(bookFromDB);
      return bookFromDB;
   }

   @Override
   @Transactional
   public void delete(int id) {
      Book book = findById(id);
      bookRepository.delete(book);
   }


}
