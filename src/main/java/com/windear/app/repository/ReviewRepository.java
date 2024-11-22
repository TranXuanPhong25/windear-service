package com.windear.app.repository;

import com.windear.app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByBookId(int bookId);
    List<Review> findAllByUserId(String userId);
    Optional<Review> findByBookIdAndUserId(Integer bookId, String userId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.bookId = :bookId")
    Double getAverageRatingOfBookById(@Param("bookId") Integer bookId);
}
