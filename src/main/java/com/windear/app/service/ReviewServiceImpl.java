package com.windear.app.service;

import com.windear.app.entity.Review;
import com.windear.app.exception.ReviewNotFoundException;
import com.windear.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> findReviewByBookId(int bookId) {
        return reviewRepository.findAllByBookId(bookId);
    }

    @Override
    public Review findReviewById(int reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new ReviewNotFoundException("Review with id not found: " + reviewId);
        }
    }

    @Override
    public Review update(Review review) {
        Review reviewFromDB = findReviewById(review.getReviewId());
        if(review.getReviewContent() != null) {
            reviewFromDB.setReviewContent(review.getReviewContent());
        }
        if(review.getBookId() != 0) {
            reviewFromDB.setBookId(review.getBookId());
        }
        if(review.getUserId()!= 0) {
            reviewFromDB.setUserId(review.getUserId());
        }
        if(review.getRating() != 0) {
            reviewFromDB.setRating(review.getRating());
        }

        return reviewRepository.save(reviewFromDB);

    }

    @Override
    public void delete(int reviewId) {
        if (findReviewById(reviewId) != null) {
            reviewRepository.deleteById(reviewId);
        }
    }




}