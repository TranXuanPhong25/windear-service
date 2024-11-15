package com.windear.app.service;

import com.windear.app.entity.InternalBook;

import java.util.List;

public interface InternalBookService {
   List<InternalBook> findAll() ;
   InternalBook findById(Integer id);
   InternalBook add(InternalBook internalBook);
   void delete(Integer id);
   List<InternalBook> findTop10ByReleaseDate();
}
