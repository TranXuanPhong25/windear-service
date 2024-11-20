package com.windear.app.dto;

public record SendEmailVerificationPayload(String client_id, String user_id) {
}