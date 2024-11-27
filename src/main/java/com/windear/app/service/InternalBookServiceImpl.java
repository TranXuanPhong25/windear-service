package com.windear.app.service;

import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.BookCopy;
import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.exception.IsbnExistsException;
import com.windear.app.repository.BookCopyRepository;
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
   private final BookCopyRepository bookCopyRepository;

   @Autowired
   public InternalBookServiceImpl(InternalBookRepository bookRepository, @Qualifier("externalBookProxy") ExternalBookService externalBookService, ReviewRepository reviewRepository, PopularBookService popularBookService, PopularBookRepository popularBookRepository, BookCopyRepository bookCopyRepository) {
      this.internalBookRepository = bookRepository;
      this.externalBookService = externalBookService;
      this.reviewRepository = reviewRepository;
      this.popularBookService = popularBookService;
      this.popularBookRepository = popularBookRepository;
      this.bookCopyRepository = bookCopyRepository;
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
         Optional<PopularBook> popularBook = popularBookRepository.findById(id);
         if(popularBook.isPresent()) {
            popularBookRepository.deleteById(id);
         }
         bookCopyRepository.deleteById(id);
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

   @Override
   public InternalBook update(InternalBook updatedBook) {
      InternalBook bookFromDB = findById(updatedBook.getId());
      if (updatedBook.getTitle() != null) {
         bookFromDB.setTitle(updatedBook.getTitle());
      }
      if (updatedBook.getAuthor() != null) {
         bookFromDB.setAuthor(updatedBook.getAuthor());
      }
      if (updatedBook.getReleaseDate() != null) {
         bookFromDB.setReleaseDate(updatedBook.getReleaseDate());
      }
      if (updatedBook.getRating() > 0) {
         bookFromDB.setRating(updatedBook.getRating());
      }
      if (updatedBook.getDescription() != null) {
         bookFromDB.setDescription(updatedBook.getDescription());
      }
      if (updatedBook.getIsbn10() != null) {
         bookFromDB.setIsbn10(updatedBook.getIsbn10());
      }
      if (updatedBook.getIsbn13() != null) {
         bookFromDB.setIsbn13(updatedBook.getIsbn13());
      }
      if (updatedBook.getImageUrl() != null) {
         bookFromDB.setImageUrl(updatedBook.getImageUrl());
      }
      if (updatedBook.getAuthorDescription() != null) {
         bookFromDB.setAuthorDescription(updatedBook.getAuthorDescription());
      }
      if (updatedBook.getPublisher() != null) {
         bookFromDB.setPublisher(updatedBook.getPublisher());
      }
      if (updatedBook.getFormat() != null) {
         bookFromDB.setFormat(updatedBook.getFormat());
      }
      if (updatedBook.getLanguage() != null) {
         bookFromDB.setLanguage(updatedBook.getLanguage());
      }
      if (updatedBook.getNumPages() != null) {
         bookFromDB.setNumPages(updatedBook.getNumPages());
      }

      return internalBookRepository.save(bookFromDB);
   }

   @Override
   public InternalBookDTO convertToDTO(InternalBook book) {
      InternalBookDTO dto = new InternalBookDTO();
      dto.setId(book.getId());
      dto.setTitle(book.getTitle());
      dto.setAuthor(book.getAuthor());
      dto.setPublisher(book.getPublisher());
      dto.setAddDate(book.getAddDate());
      dto.setReleaseDate(book.getReleaseDate());
      dto.setImageUrl(book.getImageUrl());
      return dto;
   }
}
