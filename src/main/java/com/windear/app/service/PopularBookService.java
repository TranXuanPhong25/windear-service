package com.windear.app.service;


import com.windear.app.entity.InternalBook;
import com.windear.app.entity.PopularBook;

import java.util.List;

public interface PopularBookService {
    PopularBook findById(Integer bookId);
    List<InternalBook> findTop10ByScore();
    PopularBook addBook(PopularBook book);
    PopularBook updateScore(Integer bookId, Integer score);
    void delete(Integer bookId);
}
