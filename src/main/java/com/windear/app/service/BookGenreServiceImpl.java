package com.windear.app.service;

import com.windear.app.entity.BookGenre;
import com.windear.app.primarykey.BookGenreId;
import com.windear.app.repository.BookGenreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookGenreServiceImpl implements BookGenreService {
    private final BookGenreRepository bookGenreRepository;

    @Autowired
    public BookGenreServiceImpl(BookGenreRepository bookGenreRepository) {
        this.bookGenreRepository = bookGenreRepository;
    }

    @Override
    @Transactional
    public BookGenre add(BookGenre bookGenre) {
        return bookGenreRepository.save(bookGenre);
    }

    @Override
    public BookGenre findById(BookGenreId bookGenreId) {
        Optional<BookGenre> bookGenre = bookGenreRepository.findById(bookGenreId);
        if(bookGenre.isPresent()) {
            return bookGenre.get();
        } else {
            throw new RuntimeException("bookgenre with id not found: "
                    + "BookId: " + bookGenreId.getBookId() + " "
                    + "GenreId: " + bookGenreId.getGenreId());
        }
    }

    @Override
    public List<BookGenre> findAllByBookId(Integer id) {
        return bookGenreRepository.findAllByBookId(id);
    }

    @Override
    @Transactional
    public void delete(BookGenreId id) {
        Optional<BookGenre> bookGenre = bookGenreRepository.findById(id);
        if (bookGenre.isPresent()) {
            bookGenreRepository.deleteById(id);
        } else {
            throw new RuntimeException("bookgenre with id not found: "
                    + "BookId: " + id.getBookId() + " "
                    + "GenreId: " + id.getGenreId());
        }
    }
}
