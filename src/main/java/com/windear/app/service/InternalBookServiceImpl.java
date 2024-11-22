package com.windear.app.service;

import com.windear.app.entity.InternalBook;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.exception.IsbnExistException;
import com.windear.app.exception.ReviewNotFoundException;
import com.windear.app.repository.InternalBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InternalBookServiceImpl implements InternalBookService {
   private final InternalBookRepository internalBookRepository;
   private ExternalBookService externalBookService;

   @Autowired
   public InternalBookServiceImpl(InternalBookRepository bookRepository, ExternalBookService externalBookService) {
      this.internalBookRepository = bookRepository;
      this.externalBookService = externalBookService;
   }

   @Override
   public List<InternalBook> findAll() {
      return internalBookRepository.findAll();
   }

   @Override
   public InternalBook findById(Integer id) {
      Optional<InternalBook> book = internalBookRepository.findById(id);
      if (book.isPresent()) {
         return book.get();
      } else {
         throw new BookNotFoundException("Book with id not found: " + id);
      }
   }

   @Override
   @Transactional
   public InternalBook add(InternalBook book) {
      System.out.println(externalBookService.isIsbnExist(book.getIsbn13()));
      if (externalBookService.isIsbnExist(book.getIsbn13()) ||
          internalBookRepository.existsByIsbn13(book.getIsbn13())) {
         throw new IsbnExistException("Book with isbn: " + book.getIsbn13() + " already exists.");
      }
      return internalBookRepository.save(book);
   }

   @Override
   @Transactional
   public void delete(Integer id) {
      if (findById(id) != null) {
         internalBookRepository.deleteById(id);
      }
      else {
         throw new BookNotFoundException("Book with title not found: " + id);
      }
   }

   @Override
   public List<InternalBook> findTop10ByReleaseDate() {
      return internalBookRepository.findTop10ByOrderByReleaseDateDesc();
   }
}
