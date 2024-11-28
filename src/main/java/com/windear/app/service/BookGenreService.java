package com.windear.app.service;

import com.windear.app.entity.BookGenre;
import com.windear.app.primarykey.BookGenreId;

import java.util.List;

public interface BookGenreService {

    BookGenre add(BookGenre bookGenre);
    BookGenre findById(BookGenreId id);
    List<BookGenre> findAllByBookId(Integer id);
    void delete(BookGenreId id);
}
