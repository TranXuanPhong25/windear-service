package com.windear.app.controller;

import com.windear.app.entity.Review;
import com.windear.app.service.ReviewService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/review")
    public Review createReview(@RequestBody Review review) {
        return reviewService.save(review);
    }

    @GetMapping("/review/{reviewId}")
    public Review getReview(@PathVariable int reviewId) {
        return reviewService.findReviewById(reviewId);
    }
    
    @GetMapping("/review/book/{bookId}")
    public List<Review> getReviewByBookId(@PathVariable int bookId) {
        return reviewService.findReviewByBookId(bookId);
    }

    @GetMapping("/review/{bookId}/{userId}")
    public Review getReviewByBookIdAndUserId(@PathVariable Integer bookId, @PathVariable String userId) {
        return reviewService.findReviewByBookIdAndUserId(bookId, userId);
    }

    @GetMapping("/review/rate/{bookId}/{userId}")
    public double getRate(@PathVariable Integer bookId, @PathVariable String userId) {
        return reviewService.findRateByBookIdAndUserId(bookId, userId);
    }

    @PutMapping("/review/{reviewId}")
    public Review updateReview(@RequestBody Review review, @PathVariable int reviewId) {
        review.setReviewId(reviewId);
        return reviewService.update(review);
    }

    @PutMapping("/review/rate/{bookId}/{userId}/{rating}/{userImageUrl}/{userName}")
    public Review updateRate(@PathVariable Integer bookId, @PathVariable String userId, @PathVariable double rating, @PathVariable String userImageUrl, @PathVariable String userName) {
        return reviewService.updateRate(bookId, userId, rating, userImageUrl, userName);
    }

    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable int reviewId) {
        reviewService.delete(reviewId);
    }
}
