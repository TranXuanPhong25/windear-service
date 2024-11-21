package com.windear.app.repository;

import com.windear.app.entity.BookLoan;
import com.windear.app.primarykey.BookLoanId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, BookLoanId> {
    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId")
    List<BookLoan> findAllByUserId(String userId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.bookId = :bookId")
    List<BookLoan> findAllByBookId(Integer bookId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.bookLoanId.bookId = :bookId")
    List<BookLoan> findByUserIdAndBookID(String userId, Integer bookId);
}
