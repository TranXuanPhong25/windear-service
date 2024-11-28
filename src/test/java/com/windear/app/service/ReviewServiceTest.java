package com.windear.app.service;


import com.windear.app.entity.Review;
import com.windear.app.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    private Review review;
    private Review request;
    LocalDate date = LocalDate.of(2024, 11, 20);

    @BeforeEach
    void InItData() {
        review = new Review();
        review.setReviewId(1);
        review.setUserId("user123");
        review.setBookId(101);
        review.setContent("Great book!");
        review.setRating(4.5);
        review.setCreateAt(date);
        review.setUserImageUrl("http://example.com/image.jpg");
        review.setUserName("John Doe");

        request = new Review();
        request.setUserId("user123");
        request.setBookId(101);
        request.setContent("Great book!");
        request.setRating(4.5);
        request.setCreateAt(date);
        request.setUserImageUrl("http://example.com/image.jpg");
        request.setUserName("John Doe");
    }

    @Test
    void saveTest() throws Exception {
        Mockito.when(reviewRepository.findByBookIdAndUserId(101, "user123")).thenReturn(Optional.empty());

        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review response = reviewService.save(request);

        Assertions.assertEquals(1, response.getReviewId());
        Assertions.assertEquals("user123", response.getUserId());
        Assertions.assertEquals(101, response.getBookId());
        Assertions.assertEquals(4.5, response.getRating());
        Assertions.assertEquals(date, response.getCreateAt());
        Assertions.assertEquals("Great book!", response.getContent());
        Assertions.assertEquals("http://example.com/image.jpg", response.getUserImageUrl());
        Assertions.assertEquals("John Doe", response.getUserName());

    }

    @Test
    void saveTest_ReviewExist() {
        Mockito.when(reviewRepository.findByBookIdAndUserId(101, "user123")).thenReturn(Optional.of(review));

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            reviewService.save(request);
        });

        Assertions.assertEquals("UserId user123 have reviewed the bookId: 101", exception.getMessage());
    }

    @Test
    void findReviewByBookId() {
        List<Review> reviewList = new ArrayList<>();

        Review review2 = new Review();
        review2.setReviewId(2);
        review2.setUserId("user456");
        review2.setBookId(101);
        review2.setContent("Great book!");
        review2.setRating(4.5);
        review2.setCreateAt(date);
        review2.setUserImageUrl("http://example.com/image.jpg");
        review2.setUserName("John Cena");

        reviewList.add(review);
        reviewList.add(review2);


        Mockito.when(reviewRepository.findAllByBookId(anyInt())).thenReturn(reviewList);

        List<Review> response = reviewService.findReviewByBookId(101);

        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals(response.get(0).getReviewId(), 1);
        Assertions.assertEquals(response.get(0).getBookId(), 101);
        Assertions.assertEquals(response.get(0).getUserId(), "user123");
        Assertions.assertEquals(response.get(0).getUserName(), "John Doe");
        Assertions.assertEquals(response.get(0).getCreateAt(), date);
        Assertions.assertEquals(response.get(1).getReviewId(), 2);
        Assertions.assertEquals(response.get(1).getBookId(), 101);
        Assertions.assertEquals(response.get(1).getUserId(), "user456");
        Assertions.assertEquals(response.get(1).getUserName(), "John Cena");
        Assertions.assertEquals(response.get(1).getCreateAt(), date);
    }

    @Test
    void findReviewByIdTest_Success() {
        Mockito.when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        Review respone = reviewService.findReviewById(1);

        Assertions.assertEquals(respone.getReviewId(), 1);
        Assertions.assertEquals(respone.getContent(), "Great book!");
        Assertions.assertEquals(respone.getUserId(), "user123");
        Assertions.assertEquals(respone.getBookId(), 101);
        Assertions.assertEquals(respone.getUserName(), "John Doe");
    }

    @Test
    void findReviewByIdTest_NotFound() {
        Mockito.doThrow(new RuntimeException("Review with ID not found: 1")).when(reviewRepository).findById(1);

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            reviewService.findReviewById(1);
        });

        Assertions.assertEquals("Review with ID not found: 1", exception.getMessage());
    }

    @Test
    void findReviewByBookIdAndUserIdTest() {
        Mockito.when(reviewRepository.findByBookIdAndUserId(anyInt(), anyString())).thenReturn(Optional.of(review));

        Review response = reviewService.findReviewByBookIdAndUserId(101, "user123");

        Assertions.assertEquals(1, response.getReviewId());
        Assertions.assertEquals("user123", response.getUserId());
        Assertions.assertEquals(101, response.getBookId());
        Assertions.assertEquals(4.5, response.getRating());
        Assertions.assertEquals(date, response.getCreateAt());
        Assertions.assertEquals("Great book!", response.getContent());
        Assertions.assertEquals("http://example.com/image.jpg", response.getUserImageUrl());
        Assertions.assertEquals("John Doe", response.getUserName());
    }


    @Test
    void findReviewByBookIdAndUserIdTest_NotFound() {
        Mockito.doThrow(new RuntimeException("UserId: user123 or BookId: 101 not found")).when(reviewRepository).findByBookIdAndUserId(101, "user123");

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            reviewService.findReviewByBookIdAndUserId(101, "user123");
        });

        Assertions.assertEquals("UserId: user123 or BookId: 101 not found", exception.getMessage());
    }

    @Test
    void getRate() {
        Mockito.when(reviewRepository.findByBookIdAndUserId(101, "user123"))
                .thenReturn(Optional.of(review));

        double rating = reviewService.findRateByBookIdAndUserId(101, "user123");

        Assertions.assertEquals(4.5, rating);
    }

    @Test
    void getRate_NotFound() {
        Mockito.when(reviewRepository.findByBookIdAndUserId(101, "user123"))
                .thenReturn(Optional.empty());

        double rating = reviewService.findRateByBookIdAndUserId(101, "user123");

        Assertions.assertEquals(0.0, rating);
    }

    @Test
    void updateRateUpdatesExistingReview() {
        Mockito.when(reviewRepository.findByBookIdAndUserId(101, "user123")).thenReturn(Optional.of(review));
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(review);
        Review response = reviewService.updateRate(101, "user123", 5.0, "http://example.com/image.jpg", "John Doe");

        Assertions.assertEquals(1, response.getReviewId());
        Assertions.assertEquals("user123", response.getUserId());
        Assertions.assertEquals(101, response.getBookId());
        Assertions.assertEquals(5, response.getRating());
        Assertions.assertEquals(date, response.getCreateAt());
        Assertions.assertEquals("Great book!", response.getContent());
        Assertions.assertEquals("http://example.com/image.jpg", response.getUserImageUrl());
        Assertions.assertEquals("John Doe", response.getUserName());
    }

    @Test
    void updateRateUpdatesExistingReview_NotExist() {
        Mockito.when(reviewRepository.findByBookIdAndUserId(101, "user123")).thenReturn(Optional.empty());
        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(review);
        Review response = reviewService.updateRate(101, "user123", 4.5, "http://example.com/image.jpg", "John Doe");

        Assertions.assertEquals(1, response.getReviewId());
        Assertions.assertEquals("user123", response.getUserId());
        Assertions.assertEquals(101, response.getBookId());
        Assertions.assertEquals(4.5, response.getRating());
        Assertions.assertEquals(date, response.getCreateAt());
        Assertions.assertEquals("Great book!", response.getContent());
        Assertions.assertEquals("http://example.com/image.jpg", response.getUserImageUrl());
        Assertions.assertEquals("John Doe", response.getUserName());
    }

    @Test
    void deleteReviewTest() {
        Mockito.doNothing().when(reviewRepository).deleteById(anyInt());
        Mockito.when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        reviewService.delete(1);

        Mockito.verify(reviewRepository).deleteById(1);
    }

    @Test
    void deleteReviewTest_NotFound() {
        Mockito.doThrow(new RuntimeException("Review with ID not found: 1")).when(reviewRepository).findById(anyInt());

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            reviewService.delete(1);
        });

        Assertions.assertEquals("Review with ID not found: 1", exception.getMessage());
    }











    
}
