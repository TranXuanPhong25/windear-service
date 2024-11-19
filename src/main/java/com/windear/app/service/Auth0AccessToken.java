package com.windear.app.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

public class Auth0AccessToken {
    @Value("${management.api.client.id}")
    private String clientId;

    @Value("${management.api.client.secret}")
    private String clientSecret;

    @Value("${management.api.audience.url}")
    private String audience;

    private final WebClient auth0WebClient;

    private final int ACCESS_TOKEN_EXPIRES_IN_SECONDS = 64800;
    private long expirationTime;

    private String accessToken;

    public Auth0AccessToken(@Qualifier("auth0WebClient") WebClient auth0WebClient) {
        this.auth0WebClient = auth0WebClient;
        this.expirationTime = 0;
    }


    private String refreshToken() {
        String grantType = "client_credentials";
        String requestBody = "{"
                + "\"client_id\" :\"" + clientId + "\", "
                + "\"client_secret\" :\"" + clientSecret + "\","
                + "\"audience\" :\"" + audience + "\","
                + "\"grant_type\" :\"" + grantType + "\"}";
        Map<String, String> result = auth0WebClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        if(result == null) {
            return refreshToken();
        }
        accessToken = result.get("access_token");
        expirationTime = System.currentTimeMillis() + ACCESS_TOKEN_EXPIRES_IN_SECONDS * 1000;
        return accessToken;
    }

    public String getAccessToken() {
        if(expirationTime < System.currentTimeMillis()) {
            return accessToken;
        }
        return refreshToken();
    }
}
