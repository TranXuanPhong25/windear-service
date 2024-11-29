package com.windear.app.service;

import com.windear.app.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReviewService {
    /**
     * Saves a review.
     *
     * @param review the review to save
     * @return the saved Review
     */
    Review save(Review review);

    /**
     * Finds reviews by the book ID.
     *
     * @param bookId the ID of the book
     * @return a list of Reviews for the specified book ID
     */
    List<Review> findReviewByBookId(int bookId);

    /**
     * Finds a review by its ID.
     *
     * @param reviewId the ID of the review
     * @return the Review with the specified ID
     */
    Review findReviewById(int reviewId);

    /**
     * Finds a review by the book ID and user ID.
     *
     * @param bookId the ID of the book
     * @param userId the ID of the user
     * @return the Review for the specified book ID and user ID
     */
    Review findReviewByBookIdAndUserId(Integer bookId, String userId);

    /**
     * Finds the rating by the book ID and user ID.
     *
     * @param bookId the ID of the book
     * @param userId the ID of the user
     * @return the rating for the specified book ID and user ID
     */
    double findRateByBookIdAndUserId(Integer bookId, String userId);

    /**
     * Updates the rating of a review.
     *
     * @param bookId the ID of the book
     * @param userId the ID of the user
     * @param rating the new rating
     * @param userImageUrl the URL of the user's image
     * @param userName the name of the user
     * @return the updated Review with the new rating
     */
    Review updateRate(Integer bookId, String userId, double rating, String userImageUrl, String userName);

    /**
     * Updates a review.
     *
     * @param review the review to update
     * @return the updated Review
     */
    Review update(Review review);

    /**
     * Deletes a review by its ID.
     *
     * @param reviewId the ID of the review to delete
     */
    void delete(int reviewId);
}
