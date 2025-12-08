package com.windear.app.functional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windear.app.controller.ReviewController;
import com.windear.app.entity.Review;
import com.windear.app.repository.ReviewRepository;
import com.windear.app.service.ReviewService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@DisplayName("Use Case 7: Review Tests")
class ReviewControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ReviewService reviewService;

	@MockBean
	private ReviewRepository reviewRepository;

	private Review buildReview(Integer reviewId) {
		Review review = new Review();
		review.setReviewId(reviewId);
		review.setUserId("auth0|user123");
		review.setBookId(3);
		review.setContent("Good");
		review.setRating(5.0);
		review.setUserImageUrl("http://example.com/avatar.png");
		review.setUserName("Reviewer");
		return review;
	}
	
	@Test
	void testGetReviewsByBookId() throws Exception {
		Review review = buildReview(3);
		when(reviewService.findReviewByBookId(3)).thenReturn(List.of(review));

		mockMvc.perform(get("/api/review/book/3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].bookId").value(3))
				.andExpect(jsonPath("$[0].userId").value(review.getUserId()));
	}

	@Test
	void testReviewForBook() throws Exception {
		Review requestReview = buildReview(null);
		Review createdReview = buildReview(5);
		when(reviewRepository.save(any(Review.class))).thenReturn(createdReview);

		mockMvc.perform(post("/api/review")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(requestReview)))
				.andExpect(status().isOk());
	}

	@Test
	void testUpdateRating() throws Exception {
		Review updatedReview = buildReview(5);
		updatedReview.setRating(4.5);
		when(reviewRepository.findById(5)).thenReturn(Optional.of(buildReview(5)));
		when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);
		when(reviewService.update(any(Review.class))).thenReturn(updatedReview);
		mockMvc.perform(put("/api/review/5")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updatedReview)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.rating").value(4.5));
	}

}
