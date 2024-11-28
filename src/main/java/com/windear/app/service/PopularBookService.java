package com.windear.app.service;


import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;

import java.util.List;

public interface PopularBookService {
    PopularBook findById(Integer bookId);
    List<InternalBook> findTop10ByScore();
    void addBook(PopularBook book);
    void updateScore(Integer bookId, Integer score);
    void delete(Integer bookId);
}
