package com.windear.app.service;

import com.windear.app.dto.AddInternalBookRequestDTO;
import com.windear.app.dto.InternalBookDTO;
import com.windear.app.entity.InternalBook;

import java.util.List;

public interface InternalBookService {
   /**
    * Retrieves all internal books.
    *
    * @return a list of InternalBookDTOs
    */
   List<InternalBookDTO> findAll();

   /**
    * Finds an internal book by its ID.
    *
    * @param id the ID of the internal book
    * @return the AddInternalBookRequestDTO with the specified ID
    */
   AddInternalBookRequestDTO findById(Integer id);

   /**
    * Adds a new internal book.
    *
    * @param internalBook the internal book to add
    * @return the added InternalBook
    */
   InternalBook add(AddInternalBookRequestDTO internalBook);

   /**
    * Deletes an internal book by its ID.
    *
    * @param id the ID of the internal book to delete
    */
   void delete(Integer id);

   /**
    * Finds the top 10 internal books by release date.
    *
    * @return a list of the top 10 InternalBooks by release date
    */
   List<InternalBook> findTop10ByReleaseDate();

   /**
    * Gets the number of books added in the last 30 days.
    *
    * @return the number of books added in the last 30 days
    */
   long getBookInLast30Day();

   /**
    * Updates an internal book.
    *
    * @param internalBook the internal book to update
    * @return the updated InternalBook
    */
   InternalBook update(AddInternalBookRequestDTO internalBook);

   /**
    * Converts an InternalBook entity to a DTO.
    *
    * @param book the InternalBook entity to convert
    * @return the converted InternalBookDTO
    */
   InternalBookDTO convertToDTO(InternalBook book);
}
