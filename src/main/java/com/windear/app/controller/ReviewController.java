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

    @PutMapping("/review")
    public Review updateReview(@RequestBody Review review) {
        return reviewService.update(review);
    }
    
    @DeleteMapping("/review/{reviewId}")
    public void deleteReview(@PathVariable int reviewId) {
        reviewService.delete(reviewId);
    }
}
