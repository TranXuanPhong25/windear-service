package com.windear.app.model;

public class Auth0UserProfile extends UserProfile {
    private String username;
    private String email;

    public Auth0UserProfile() {
        // Default constructor
    }

    public Auth0UserProfile(String username, String email, UserMetadata user_metadata) {
        super(user_metadata);
        this.username = username;
        this.email = email;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }
}
