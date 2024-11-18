package com.windear.app.repository;

import com.windear.app.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findAllByBookId(int bookId);
    Optional<Review> findByBookIdAndUserId(Integer bookId, String userId);
}
