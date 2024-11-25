package com.windear.app.model;

public class UsernameAuth0UserProfile extends UserProfile{
    private String username;

    public UsernameAuth0UserProfile() {
        // Default constructor
    }

    public UsernameAuth0UserProfile(String username, UserMetadata user_metadata) {
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
