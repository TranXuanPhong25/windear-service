package com.windear.app.service;

import com.windear.app.entity.Review;
import com.windear.app.exception.ReviewNotFoundException;
import com.windear.app.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReviewBoundaryTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void update_ShouldNotUpdateRating_WhenRatingIsZero() {
        Review existingReview = new Review("user1", 1, "Content", 4.0, LocalDate.now(), "img.jpg", "User");
        existingReview.setReviewId(1);
        
        Review updateRequest = new Review();
        updateRequest.setReviewId(1);
        updateRequest.setRating(0.0); // Boundary 0
        updateRequest.setContent("Updated content");
        
        when(reviewRepository.findById(1)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);

        reviewService.update(updateRequest);

        assertEquals(4.0, existingReview.getRating(), "Rating should be updated to 0.0");
        assertEquals("Updated content", existingReview.getContent());
    }

    @Test
    void save_ShouldReject_WhenRatingIsNotInRange() {
        Review outRangeRating = new Review("user2", 2, "Bad", -5.0, null, "img.jpg", "User2");
        
        when(reviewRepository.findByBookIdAndUserId(2, "user2")).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(outRangeRating);

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.save(outRangeRating);
        });
        outRangeRating.setRating(100.0);
        when(reviewRepository.save(any(Review.class))).thenReturn(outRangeRating);
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.save(outRangeRating);
        });
    }

    @Test
    void update_ShouldNotUpdateContent_WhenContentIsNull() {
        Review existingReview = new Review("user3", 3, "Old content", 4.0, LocalDate.now(), "img.jpg", "User3");
        existingReview.setReviewId(1);

        Review updateRequest = new Review();
        updateRequest.setReviewId(1);
        updateRequest.setContent(null); // Boundary value for content
        updateRequest.setRating(5.0);

        when(reviewRepository.findById(1)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);

        reviewService.update(updateRequest);

        assertEquals("Old content", existingReview.getContent());
        assertEquals(5.0, existingReview.getRating());
    }

    @Test
    void save_ShouldAccept_WhenContentIsEmptyString() {
        Review emptyContentReview = new Review("user4", 4, "", 4.0, null, "img.jpg", "User4");
        
        when(reviewRepository.findByBookIdAndUserId(4, "user4")).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(emptyContentReview);

        Review result = reviewService.save(emptyContentReview);

        assertEquals("", result.getContent());
    }

    @Test
    void save_ShouldNotAccept_WhenContentIsExtremelyLong() {
        String longContent = "a".repeat(10_000_000); // 10 million chars
        Review longReview = new Review("user5", 5, longContent, 4.0, null, "img.jpg", "User5");
        
        when(reviewRepository.findByBookIdAndUserId(5, "user5")).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(longReview);

        Review result = reviewService.save(longReview);

        assertNotEquals(10_000_000, result.getContent().length());
    }


    @Test
    void save_ShouldNotAccept_WhenUserIdIsInvalid() {
        Review nullUserReview = new Review(null, 6, "Content", 4.0, null, "img.jpg", "User6");
        
        when(reviewRepository.findByBookIdAndUserId(6, null)).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(nullUserReview);

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.save(nullUserReview);
        });
        nullUserReview.setUserId("");
        when(reviewRepository.save(any(Review.class))).thenReturn(nullUserReview);
        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.save(nullUserReview);
        });
    }

    @Test
    void update_ShouldNotUpdateBookId_WhenBookIdIsNull() {
        Review existingReview = new Review("user12", 7, "Content", 4.0, LocalDate.now(), "img.jpg", "User7");
        existingReview.setReviewId(1);
        
        Review updateRequest = new Review();
        updateRequest.setReviewId(1);
        updateRequest.setBookId(null); // Boundary null
        updateRequest.setContent("New content");
        
        when(reviewRepository.findById(1)).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(existingReview);

        reviewService.update(updateRequest);

        assertEquals(7, existingReview.getBookId());
    }

    @Test
    void save_ShouldNotAccept_WhenBookIdIsInvalid() {
        Review zeroBookReview = new Review("user8", 0, "Content", 4.0, null, "img.jpg", "User8");
        
        when(reviewRepository.findByBookIdAndUserId(0, "user8")).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(zeroBookReview);

        Review result = reviewService.save(zeroBookReview);

        assertNotEquals(0, result.getBookId());
        Review negativeBookReview = new Review("user9", -5, "Content", 4.0, null, "img.jpg", "User9");

        when(reviewRepository.findByBookIdAndUserId(-5, "user9")).thenReturn(Optional.empty());
        when(reviewRepository.save(any(Review.class))).thenReturn(negativeBookReview);

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.save(negativeBookReview);
        });
    }

    @Test
    void findRateByBookIdAndUserId_ShouldReturnZero_WhenReviewNotFound() {
        when(reviewRepository.findByBookIdAndUserId(10, "user10")).thenReturn(Optional.empty());

        double result = reviewService.findRateByBookIdAndUserId(10, "user10");

        assertEquals(0.0, result);
    }
}
