package com.windear.app.model;

public class UserProfilePayload {
    String isSocial;
    String payload;

    public UserProfilePayload() {

    }

    public UserProfilePayload(String isSocial, String payload) {
        this.isSocial = isSocial;
        this.payload = payload;
    }
    public String getIsSocial() {
        return isSocial;
    }

    public String getPayload() {
        return payload;
    }
}
