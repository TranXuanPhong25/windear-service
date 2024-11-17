package com.windear.app.model;

public class EmailAuth0UserProfile extends UserProfile{
    private String username;

    public EmailAuth0UserProfile() {
        // Default constructor
    }

    public EmailAuth0UserProfile(String username, String email, UserMetadata user_metadata) {
        super(user_metadata);
        this.username = username;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Setter for username
    public void setUsername(String username) {
        this.username = username;
    }

}
