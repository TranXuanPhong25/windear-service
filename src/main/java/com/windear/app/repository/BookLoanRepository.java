package com.windear.app.repository;

import com.windear.app.entity.BookLoan;
import com.windear.app.model.AnalyticStat;
import com.windear.app.primarykey.BookLoanId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, BookLoanId> {
    @NotNull
    @Query("SELECT b FROM BookLoan b ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findAll();

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId ORDER BY b.bookLoanId.requestDate DESC")
    List<BookLoan> findAllByUserId(String userId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.bookId = :bookId")
    List<BookLoan> findAllByBookId(Integer bookId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.bookLoanId.bookId = :bookId")
    List<BookLoan> findByUserIdAndBookID(String userId, Integer bookId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.status = com.windear.app.enums.Status.PENDING")
    List<BookLoan> findBorrowRequestByUserId(String userId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.status = com.windear.app.enums.Status.ACCEPT AND b.returnDate IS NULL")
    List<BookLoan> findBorrowedBookByUserId(String userId);

    @Query("SELECT b FROM BookLoan b WHERE b.bookLoanId.userId = :userId AND b.status = com.windear.app.enums.Status.ACCEPT AND b.returnDate IS NOT NULL")
    List<BookLoan> findReturnedBookByUserId(String userId);

    @Query(
            value = "SELECT DATE(TO_TIMESTAMP(borrow_date / 1000) AT TIME ZONE 'UTC' AT TIME ZONE 'GMT+7') AS time, \n" +
                    "COUNT(book_id) AS value\n" +
                    "FROM book_loan\n" +
                    "WHERE borrow_date IS NOT NULL \n" +
                    "AND DATE(TO_TIMESTAMP(borrow_date / 1000) AT TIME ZONE 'UTC' AT TIME ZONE 'GMT+7') >= (NOW() - INTERVAL '30 days')\n" +
                    "GROUP BY time\n" +
                    "ORDER BY time",
            nativeQuery = true
    )
    List<Object[]> getBorrowStatsIn30Days();

    @Query(
            value = "SELECT DATE(TO_TIMESTAMP(return_date / 1000) AT TIME ZONE 'UTC' AT TIME ZONE 'GMT+7') AS time, \n" +
                    "COUNT(book_id) AS value\n" +
                    "FROM book_loan\n" +
                    "WHERE return_date IS NOT NULL\n" +
                    "AND DATE(TO_TIMESTAMP(return_date / 1000) AT TIME ZONE 'UTC' AT TIME ZONE 'GMT+7') >= (NOW() - INTERVAL '30 days')\n" +
                    "GROUP BY time\n" +
                    "ORDER BY time",
            nativeQuery = true
    )
    List<Object[]> getReturnStatsIn30Days();
}
