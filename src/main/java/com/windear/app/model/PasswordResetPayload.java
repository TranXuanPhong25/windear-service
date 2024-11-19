package com.windear.app.model;

public record PasswordResetPayload(String client_id,String user_id ,int ttl_sec) {
}
