package com.windear.app.entity;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer reviewId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "content", nullable = true,columnDefinition = "TEXT")
    private String content;

    @Column(name = "rating")
    private double rating;

    @Column(name = "create_at")
    private LocalDate createAt;

    @Column(name = "user_image_url", nullable = true,columnDefinition = "TEXT")
    private String userImageUrl;

    @Column(name = "user_name")
    private String userName;

    public Review() {}

    public Review(String userId, Integer bookId, String content, double rating, LocalDate createAt, String userImageUrl, String userName) {
        this.userId = userId;
        this.bookId = bookId;
        this.content = content;
        this.rating = rating;
        this.createAt = createAt;
        this.userImageUrl = userImageUrl;
        this.userName = userName;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}