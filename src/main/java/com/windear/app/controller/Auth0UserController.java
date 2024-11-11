package com.windear.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.windear.app.model.Message;
import com.windear.app.model.PasswordResetPayload;
import com.windear.app.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth0")
public class Auth0UserController {
    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.client.id}")
    private String auth0CilentId;

    private final WebClient webClient;
    private final int TICKET_TIME_OUT_DURATION = 300;
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
    public ResponseEntity<String> getUserLists() {
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

    @PutMapping("/user/profile/{userId}")
    public ResponseEntity<String> updateProfile(@RequestBody String data, @PathVariable String userId) {
        String requestBody = "{"
                + "\"client_id\" :\"" + clientId + "\", "
                + "\"client_secret\" :\"" + clientSecret + "\","
                + "\"audience\" :\"" + audience + "\","
                + "\"grant_type\" :\"" + grantType + "\"}";

        // First request to get the access token
        Map<String, String> result = webClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        String accessToken = result.get("access_token");

        // Second request to update the user's profile
        try {
            UserProfile payload = objectMapper.readValue(data, UserProfile.class);

            String users = webClient.patch()
                    .uri("/api/v2/users/" + userId)
                    .header("authorization", "Bearer " + accessToken)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class).map(errorBody -> {
                                throw new RuntimeException("Error updating profile: " + errorBody);
                            })
                    )
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok("Updated Profile");

        } catch (WebClientResponseException e) {
            // Handle specific HTTP error responses here
            return ResponseEntity.status(e.getStatusCode())
                    .body("Failed to update profile: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable String userId) {
        String requestBody = "{"
                + "\"client_id\" :\"" + clientId + "\", "
                + "\"client_secret\" :\"" + clientSecret + "\","
                + "\"audience\" :\"" + audience + "\","
                + "\"grant_type\" :\"" + grantType + "\"}";

        // First request to get the access token
        Map<String, String> result = webClient.post()
                .uri("/oauth/token")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        String accessToken = result.get("access_token");

        // Second request to update the user's profile
        try {
            PasswordResetPayload payload = new PasswordResetPayload(auth0CilentId, userId, TICKET_TIME_OUT_DURATION);
            String ticket = webClient.post()
                    .uri("/api/v2/tickets/password-change")
                    .header("authorization", "Bearer " + accessToken)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class).map(errorBody -> {
                                throw new RuntimeException("Error get ticket: " + errorBody);
                            })
                    )
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(ticket);

        } catch (WebClientResponseException e) {
            // Handle specific HTTP error responses here
            return ResponseEntity.status(e.getStatusCode())
                    .body("Failed to receive ticket: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}
