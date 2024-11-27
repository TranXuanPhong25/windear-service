package com.windear.app.repository;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, BookLoanId> {
    @Query("SELECT b FROM BookLoan b ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findAll();

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findAllByUserId(String userId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.bookId = :bookId ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findAllByBookId(Integer bookId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.bookLoanId.bookId = :bookId ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findByUserIdAndBookID(String userId, Integer bookId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.status = com.windear.app.enums.Status.PENDING ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findBorrowRequestByUserId(String userId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.status = com.windear.app.enums.Status.ACCEPT AND b.returnDate IS NULL ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findBorrowedBookByUserId(String userId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.status = com.windear.app.enums.Status.ACCEPT AND b.returnDate IS NOT NULL ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findReturnedBookByUserId(String userId);
}
