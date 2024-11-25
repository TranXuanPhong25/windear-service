package com.windear.app.dto;

public class SubscribeRequest {
    private String userId;
    private Integer bookId;
    private String title;
    private String authorName;

    public SubscribeRequest() {

    }

    public SubscribeRequest(String userId, Integer bookId, String title, String authorName) {
        this.userId = userId;
        this.bookId = bookId;
        this.title = title;
        this.authorName = authorName;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
