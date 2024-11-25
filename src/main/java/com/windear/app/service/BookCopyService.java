package com.windear.app.service;

import com.windear.app.entity.BookCopy;

public interface BookCopyService {
    BookCopy addBookCopy(Integer id, Integer quantity);
    BookCopy getBookCopyById(Integer id);
    int getQuantityOfBookCopy(Integer id);
    BookCopy modifyQuantityOfBookCopy(Integer id, Integer value);
}
