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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

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
    
}
