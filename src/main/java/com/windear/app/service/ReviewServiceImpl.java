package com.windear.app.service;

import com.windear.app.entity.Review;
import com.windear.app.exception.ReviewNotFoundException;
import com.windear.app.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        review.setCreateAt(LocalDate.now());
        Optional<Review> reviewFromDB = reviewRepository.findByBookIdAndUserId(review.getBookId(), review.getUserId());
        if (reviewFromDB.isPresent()) {
            throw new RuntimeException("UserId " + review.getUserId() + " have reviewed the bookId: " + review.getBookId());
        }
        else return reviewRepository.save(review);
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
    public Review findReviewByBookIdAndUserId(Integer bookId, String userId) {
        Optional<Review> review = reviewRepository.findByBookIdAndUserId(bookId, userId);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new ReviewNotFoundException("UserId: " + userId + " or BookId: " + bookId +" not found");
        }
    }

    @Override
    public double findRateByBookIdAndUserId(Integer bookId, String userId) {
        Optional<Review> review = reviewRepository.findByBookIdAndUserId(bookId, userId);
        if (review.isPresent()) {
            return review.get().getRating();
        }
        return 0;
    }

    @Override
    public Review updateRate(Review review) {
        Optional<Review> reviewFromDb = reviewRepository.findByBookIdAndUserId(review.getBookId(), review.getUserId());
        if(reviewFromDb.isPresent()) {
            reviewFromDb.get().setRating(review.getRating());
            return reviewRepository.save(reviewFromDb.get());
        } else {
            return reviewRepository.save(review);
        }
    }

    @Override
    public Review update(Review review) {
        Review reviewFromDB = findReviewById(review.getReviewId());
        if(review.getContent() != null) {
            reviewFromDB.setContent(review.getContent());
        }
        if(review.getBookId() != null) {
            reviewFromDB.setBookId(review.getBookId());
        }
        if(review.getUserId()!= null) {
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
