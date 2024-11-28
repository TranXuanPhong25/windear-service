package com.windear.app.service;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.InternalBook;

import java.util.List;

public interface InternalBookService {
   List<InternalBookDTO> findAll() ;
   AddInternalBookRequestDTO findById(Integer id);
   InternalBook add(AddInternalBookRequestDTO internalBook);
   void delete(Integer id);
   List<InternalBook> findTop10ByReleaseDate();
   long getBookInLast30Day();
   InternalBook update(AddInternalBookRequestDTO internalBook);
   InternalBookDTO convertToDTO(InternalBook book);
}
