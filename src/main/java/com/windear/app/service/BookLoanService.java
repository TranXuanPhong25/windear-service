package com.windear.app.service;

import com.windear.app.dto.SubscribeRequest;
import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;

import java.util.List;

/**
 * Service interface for managing book loans.
 */
public interface BookLoanService {
    /**
     * Finds a book loan by its ID.
     *
     * @param id the ID of the book loan
     * @return the BookLoan with the specified ID
     */
    BookLoan findById(BookLoanId id);

    /**
     * Finds all book loans.
     *
     * @return a list of all BookLoans
     */
    List<BookLoan> findAll();

    /**
     * Finds all book loans by user ID.
     *
     * @param userId the ID of the user
     * @return a list of BookLoans associated with the specified user ID
     */
    List<BookLoan> findAllByUserId(String userId);

    /**
     * Finds all book loans by book ID.
     *
     * @param bookId the ID of the book
     * @return a list of BookLoans associated with the specified book ID
     */
    List<BookLoan> findAllByBookId(Integer bookId);

    /**
     * Finds all book loans by user ID and book ID.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book
     * @return a list of BookLoans associated with the specified user ID and book ID
     */
    List<BookLoan> findAllByUserIdAndBookId(String userId, Integer bookId);

    /**
     * Finds all borrow requests.
     *
     * @return a list of all borrow requests
     */
    List<BookLoan> findAllBorrowRequest();

    /**
     * Finds all active book loans.
     *
     * @return a list of all active BookLoans
     */
    List<BookLoan> findAllActiveBookLoan();

    /**
     * Gets borrow requests by user ID.
     *
     * @param userId the ID of the user
     * @return a list of borrow requests associated with the specified user ID
     */
    List<BookLoan> getBorrowRequestByUserId(String userId);

    /**
     * Gets borrowed books by user ID.
     *
     * @param userId the ID of the user
     * @return a list of borrowed books associated with the specified user ID
     */
    List<BookLoan> getBorrowedBookByUserId(String userId);

    /**
     * Gets returned books by user ID.
     *
     * @param userId the ID of the user
     * @return a list of returned books associated with the specified user ID
     */
    List<BookLoan> getReturnedBookByUserId(String userId);

    /**
     * Gets subscribe requests of a book by book ID.
     *
     * @param bookId the ID of the book
     * @return a list of subscribe requests associated with the specified book ID
     */
    List<BookLoan> getSubscribeRequestOfBook(Integer bookId);

    /**
     * Gets a borrow request by user ID and book ID.
     *
     * @param userId the ID of the user
     * @param bookId the ID of the book
     * @return the BookLoan associated with the specified user ID and book ID
     */
    BookLoan getBorrowRequestByUserIdAndBookId(String userId, Integer bookId);

    /**
     * Deletes subscribe requests of a book by book ID.
     *
     * @param bookId the ID of the book
     */
    void deleteSubscribeRequestOfBook(Integer bookId);

    /**
     * Adds a new book loan.
     *
     * @param bookLoan the book loan to add
     * @return the added BookLoan
     */
    BookLoan add(BookLoan bookLoan);

    /**
     * Sends a borrow request.
     *
     * @param bookLoan the book loan to send as a borrow request
     * @return the sent BookLoan
     */
    BookLoan sendBorrowRequest(BookLoan bookLoan);

    /**
     * Accepts a borrow request by book loan ID.
     *
     * @param bookLoanId the ID of the book loan to accept
     * @return the accepted BookLoan
     */
    BookLoan acceptBorrowRequest(BookLoanId bookLoanId);

    /**
     * Declines a borrow request by book loan ID.
     *
     * @param bookLoanId the ID of the book loan to decline
     * @return the declined BookLoan
     */
    BookLoan declineBorrowRequest(BookLoanId bookLoanId);

    /**
     * Subscribes to a book to get notification.
     *
     * @param request the subscribe request
     * @return the BookLoan representing the subscription
     */
    BookLoan subscribeToBook(SubscribeRequest request);

    /**
     * Returns a book by book loan ID.
     *
     * @param bookLoanId the ID of the book loan to return
     * @return the returned BookLoan
     */
    BookLoan returnBook(BookLoanId bookLoanId);

    /**
     * Gets the available copies of a book by book ID.
     *
     * @param bookId the ID of the book
     * @return the number of available copies of the book
     */
    Integer getAvailableCopiesOfBook(Integer bookId);

    /**
     * Deletes a book loan by book loan ID.
     *
     * @param bookLoanId the ID of the book loan to delete
     */
    void deleteBookLoan(BookLoanId bookLoanId);
}
