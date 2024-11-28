package com.windear.app.service;

import com.windear.app.entity.BookCopy;

public interface BookCopyService {
    /**
     * Adds a new book copy with the specified quantity.
     *
     * @param id the ID of the book
     * @param quantity the quantity of the book copies to add
     * @return the added BookCopy
     */
    BookCopy addBookCopy(Integer id, Integer quantity);

    /**
     * Retrieves a book copy by its ID.
     *
     * @param id the ID of the book copy
     * @return the BookCopy with the specified ID
     */
    BookCopy getBookCopyById(Integer id);

    /**
     * Retrieves the quantity of a book copy by its ID.
     *
     * @param id the ID of the book copy
     * @return the quantity of the book copy
     */
    int getQuantityOfBookCopy(Integer id);

    /**
     * Modifies the quantity of a book copy by its ID.
     *
     * @param id the ID of the book copy
     * @param value the value to modify the quantity by
     */
    void modifyQuantityOfBookCopy(Integer id, Integer value);
}
