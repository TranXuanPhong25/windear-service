package com.windear.app.repository;

import com.windear.app.entity.InternalBook;
import com.windear.app.model.AnalyticStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternalBookRepository extends JpaRepository<InternalBook, Integer> {
    List<InternalBook> findTop10ByOrderByReleaseDateDesc();

    boolean existsByIsbn13(String isbn13);

    @Query("SELECT COUNT(b) FROM InternalBook b WHERE b.releaseDate >= :startDate")
    long countBooksInLast30Days(@Param("startDate") LocalDate startDate);

    @Query(
            value = "SELECT DATE(DATE_TRUNC('day', add_date)) AS time, COUNT(id) AS value " +
                    "FROM internal_book " +
                    "WHERE add_date >= (NOW() - INTERVAL '30 days') " +
                    "GROUP BY time " +
                    "ORDER BY time",
            nativeQuery = true
    )
    List<Object[]> getStatsInLast30Days();
}