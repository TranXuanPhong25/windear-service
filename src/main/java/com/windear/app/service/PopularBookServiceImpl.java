package com.windear.app.service;

import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;
import com.windear.app.exception.BookNotFoundException;
import com.windear.app.repository.PopularBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PopularBookServiceImpl implements PopularBookService{
    private final PopularBookRepository bookRepository;

    @Autowired
    public PopularBookServiceImpl(PopularBookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public List<PopularBook> findTop10ByScore() {
        return bookRepository.findTop10ByOrderByScoreDesc();
    }

    @Override
    @Transactional
    public PopularBook updateScore(Integer bookId, Integer score) {
        PopularBook bookFromDB = findById(bookId);
        if (score != null) {
            bookFromDB.setScore(score + bookFromDB.getScore());
        }
        return bookRepository.save(bookFromDB);
    }

    @Override
    @Transactional
    public void delete(Integer bookId) {
        bookRepository.deleteById(bookId);
    }

}
