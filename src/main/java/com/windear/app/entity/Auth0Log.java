package com.windear.app.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "auth0_log", schema = "public")
public class Auth0Log {

    @Id
    @Column(name = "_id", nullable = false, unique = true)
    private String _id;

    @Column(name = "user_id")
    private String user_id;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "user_agent", nullable = false)
    private String user_agent;

    @JsonProperty("isMobile") // Ensure JSON field is 'isMobile'
    @Column(name = "is_mobile", nullable = false)
    private boolean isMobile;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "ip", nullable = false)
    private String ip;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_agent() {
        return user_agent;
    }

    public void setUser_agent(String userAgent) {
        this.user_agent = userAgent;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
