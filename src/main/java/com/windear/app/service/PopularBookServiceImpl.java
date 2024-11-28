package com.windear.app.service;

import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.repository.InternalBookRepository;
import com.windear.app.repository.PopularBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PopularBookServiceImpl implements PopularBookService {
    private final PopularBookRepository bookRepository;
    private final InternalBookRepository internalBookRepository;

    @Autowired
    public PopularBookServiceImpl(PopularBookRepository bookRepository,
                                  InternalBookRepository internalBookRepository) {
        this.bookRepository = bookRepository;
        this.internalBookRepository = internalBookRepository;
    }

    @Override
    @Transactional
    public PopularBook addBook(PopularBook book) {
        return bookRepository.save(book);
    }

    @Override
    public PopularBook findById(Integer bookId) {
        Optional<PopularBook> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new BookNotFoundException("Book with id not found: " + bookId);
        }
    }

    @Override
    public List<InternalBook> findTop10ByScore() {
        List<PopularBook> popularBooks = bookRepository.findTop10ByOrderByScoreDesc();
        List<InternalBook> internalBooks = new ArrayList<>();
        for (PopularBook it : popularBooks) {
            Optional<InternalBook> internalBook = internalBookRepository.findById(it.getBookId());
            if (internalBook.isPresent()) {
                internalBooks.add(internalBook.get());
            } else {
                throw new BookNotFoundException("Book with id not found: "
                        + it.getBookId());
            }
        }
        return internalBooks;
    }

    @Override
    @Transactional
    public PopularBook updateScore(Integer bookId, Integer score) {
        Optional<PopularBook> popularBook = bookRepository.findById(bookId);
        if (popularBook.isPresent()) {
            if (score != null) {
                popularBook.get().setScore(score + popularBook.get().getScore());
            }
            return bookRepository.save(popularBook.get());
        } else {
            PopularBook newPopularBook = new PopularBook(bookId, 1);
            return addBook(newPopularBook);
        }
    }

    @Override
    @Transactional
    public void delete(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

}
