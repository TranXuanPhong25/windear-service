package com.windear.app.model;

public class UserProfilePayload {
    boolean isSocial;
    String payload;

    public UserProfilePayload() {

    }

    public UserProfilePayload(boolean isSocial, String payload) {
        this.isSocial = isSocial;
        this.payload = payload;
    }
    public boolean getIsSocial() {
        return isSocial;
    }

    public String getPayload() {
        return payload;
    }
}
