package com.windear.app.service;

import com.windear.app.entity.BookCopy;
import com.windear.app.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookCopyServiceImpl implements BookCopyService {
    private final BookCopyRepository bookCopyRepository;

    @Autowired
    public BookCopyServiceImpl(BookCopyRepository bookCopyRepository) {
        this.bookCopyRepository = bookCopyRepository;
    }


    @Override
    public BookCopy addBookCopy(Integer id, Integer quantity) {
        BookCopy book = new BookCopy(id, quantity);
        return bookCopyRepository.save(book);
    }

    @Override
    public BookCopy getBookCopyById(Integer id) {
        Optional<BookCopy> book = bookCopyRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            return addBookCopy(id, 12);
        }
    }

    @Override
    public int getQuantityOfBookCopy(Integer id) {
        BookCopy book = getBookCopyById(id);
        return book.getQuantity();
    }

    @Override
    public void modifyQuantityOfBookCopy(Integer id, Integer value) {
        BookCopy bookCopy = getBookCopyById(id);
        bookCopy.setQuantity(bookCopy.getQuantity() + value);
        bookCopyRepository.save(bookCopy);
    }
}
