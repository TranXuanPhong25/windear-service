package com.windear.app.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "notification", schema = "public")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "title")
    private String title;

    @Column(name = "is_read")
    private boolean isRead;

    public Notification() {

    }

    public Notification(Integer id, String userId, Timestamp timestamp, String title, boolean isRead) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.title = title;
        this.isRead = isRead;
    }

    public Notification(String userId, String title, Timestamp timestamp, boolean isRead) {
        this.userId = userId;
        this.title = title;
        this.timestamp = timestamp;
        this.isRead = isRead;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
