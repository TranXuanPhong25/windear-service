package com.windear.app.service;

import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.exception.IsbnExistsException;
import com.windear.app.repository.InternalBookRepository;
import com.windear.app.repository.PopularBookRepository;
import com.windear.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InternalBookServiceImpl implements InternalBookService {
   private final InternalBookRepository internalBookRepository;
   private final ReviewRepository reviewRepository;
   private final ExternalBookService externalBookService;
   private final PopularBookService popularBookService;
   private final PopularBookRepository popularBookRepository;

   @Autowired
   public InternalBookServiceImpl(InternalBookRepository bookRepository, @Qualifier("externalBookProxy") ExternalBookService externalBookService, ReviewRepository reviewRepository) {
      this.internalBookRepository = bookRepository;
      this.externalBookService = externalBookService;
      this.reviewRepository = reviewRepository;
      this.popularBookService = popularBookService;
      this.popularBookRepository = popularBookRepository;
   }

   @Override
   public List<InternalBook> findAll() {
      return internalBookRepository.findAll();
   }

   @Override
   public InternalBook findById(Integer id) {
      Optional<InternalBook> book = internalBookRepository.findById(id);
      if (book.isPresent()) {
         double rating = 0;
         Double averageRating = reviewRepository.getAverageRatingOfBookById(id);
         if(averageRating!=null){
            rating=averageRating;
         }
         book.get().setRating(rating);
         popularBookService.updateScore(id, 5);
         return book.get();
      } else {
         throw new BookNotFoundException("Book with id not found: " + id);
      }
   }

   @Override
   @Transactional
   public InternalBook add(InternalBook book) {
      if (externalBookService.isIsbnExist(book.getIsbn13()) ||
          internalBookRepository.existsByIsbn13(book.getIsbn13())) {
         throw new IsbnExistsException("Book with isbn: " + book.getIsbn13() + " already exists.");
      }
      book.setAddDate(LocalDate.now());
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

   @Override
   public long getBookInLast30Day() {
      LocalDate now = LocalDate.now();
      LocalDate startDate = now.minusDays(30);
      return internalBookRepository.countBooksInLast30Days(startDate);
   }
}
