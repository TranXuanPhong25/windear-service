package com.windear.app.service;

import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.InternalBook;

import java.util.List;

public interface InternalBookService {
   List<InternalBook> findAll() ;
   InternalBook findById(Integer id);
   InternalBook add(InternalBook internalBook);
   void delete(Integer id);
   List<InternalBook> findTop10ByReleaseDate();
   long getBookInLast30Day();
   InternalBook update(InternalBook internalBook);
   InternalBookDTO convertToDTO(InternalBook book);
}
