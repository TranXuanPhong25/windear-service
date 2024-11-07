package com.windear.app.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth0")
public class Auth0UserController {

    private final WebClient webClient;

    @Value("${management.api.client.id}")
    private String clientId;

    @Value("${management.api.client.secret}")
    private String clientSecret;

    @Value("${management.api.audience.url}")
    private String audience;

    private final String grantType = "client_credentials";

    // Initialize WebClient with a factory
    public Auth0UserController(WebClient.Builder webClientBuilder, @Value("${management.api.base.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @GetMapping(value = "/users")
    public ResponseEntity<String> searchBooks() {
        String requestBody = "{"
                + "\"client_id\" :\"" + clientId + "\", "
                + "\"client_secret\" :\"" + clientSecret + "\","
                + "\"audience\" :\"" + audience + "\","
                + "\"grant_type\" :\"" + grantType + "\"}";
        Map<String, String> result = webClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        String accessToken = result.get("access_token");
        List<Map<String, Object>> users = webClient.get()
                .uri("/api/v2/users")
                .header("authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                })
                .block();
        return ResponseEntity.ok(users.toString());
    }
}
