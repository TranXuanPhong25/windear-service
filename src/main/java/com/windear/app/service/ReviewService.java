package com.windear.app.service;

import com.windear.app.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {
    Review save(Review review);
    List<Review> findReviewByBookId(int bookId);
    Review findReviewById(int reviewId);
    Review findReviewByBookIdAndUserId(Integer bookId, String userId);
    double findRateByBookIdAndUserId(Integer bookId, String userId);
    Review updateRate(Review review);
    Review update(Review review);
    void delete(int reviewId);
}
