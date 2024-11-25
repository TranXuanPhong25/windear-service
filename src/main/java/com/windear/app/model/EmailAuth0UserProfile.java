package com.windear.app.model;

public class EmailAuth0UserProfile extends UserProfile{
    private String email;

    public EmailAuth0UserProfile() {
        // Default constructor
        super();
    }

    public EmailAuth0UserProfile(String email, UserMetadata user_metadata) {
        super(user_metadata);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
