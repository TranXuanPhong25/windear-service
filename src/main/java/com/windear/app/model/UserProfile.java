package com.windear.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class UserProfile {
    @JsonProperty("user_metadata") // Ensure correct serialization name
    protected UserMetadata user_metadata;
    public UserProfile() {}
    public UserProfile(UserMetadata user_metadata) {
        this.user_metadata = user_metadata;
    }
    public UserMetadata getUser_metadata() {
        return user_metadata;
    }
    public void setUser_metadata(UserMetadata user_metadata) {
        this.user_metadata = user_metadata;
    }
}