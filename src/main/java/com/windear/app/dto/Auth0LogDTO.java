package com.windear.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth0LogDTO {

    private String date;
    private String ip;
    private String user_agent;
    private String user_id;
    private String user_name;

    @JsonProperty("isMobile") // Ensure JSON field is 'isMobile'
    private boolean isMobile;
    private String type;

    // Constructor
    public Auth0LogDTO(String date, String ip, String userAgent, String userId, String userName, boolean isMobile, String type) {
        this.date = date;
        this.ip = ip;
        this.user_agent = userAgent;
        this.user_id = userId;
        this.user_name = userName;
        this.isMobile = isMobile;
        this.type = type;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String user_agent) {
        this.user_agent = user_agent;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public boolean isMobile() {
        return isMobile;
    }

    public void setMobile(boolean mobile) {
        isMobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
