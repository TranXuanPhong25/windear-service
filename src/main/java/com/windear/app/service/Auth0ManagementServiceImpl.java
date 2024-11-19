package com.windear.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.windear.app.dto.SendEmailVerificationPayload;
import com.windear.app.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
public class Auth0ManagementServiceImpl implements Auth0ManagementService {
    private String accessToken;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.client.id}")
    private String auth0ClientId;

    private final WebClient webClient;
    @Value("${management.api.client.id}")
    private String clientId;

    @Value("${management.api.client.secret}")
    private String clientSecret;

    @Value("${management.api.audience.url}")
    private String audience;

    private final String grantType = "client_credentials";

    // Initialize WebClient with a factory
    public Auth0ManagementServiceImpl(WebClient.Builder webClientBuilder, @Value("${management.api.base.url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    private String getAccessToken() {
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
        assert result != null;
        return result.get("access_token");
    }

    @Override
    public String getUsers() {
        accessToken = getAccessToken();
        return webClient.get()
                 .uri("/api/v2/users?include_totals=true")
                 .header("authorization", "Bearer " + accessToken)
                 .retrieve()
                 .bodyToMono(String.class)
                 .block();
    }

    @Override
    public ResponseEntity<String> deleteUser(String id) {
        accessToken = getAccessToken();

        try {
            String response = webClient.delete()
                    .uri("/api/v2/users/" + id)
                    .header("authorization", "Bearer " + accessToken)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class).handle((errorBody, sink) -> {
                                sink.error(new RuntimeException("Error delete user: " + errorBody));
                            })
                    )
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(response);

        } catch (WebClientResponseException e) {
            // Handle specific HTTP error responses here
            return ResponseEntity.status(e.getStatusCode())
                    .body("Failed to delete user: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> resetPassword(String id) {
        accessToken = getAccessToken();
        try {
            int TICKET_TIME_OUT_DURATION = 300;
            PasswordResetPayload payload = new PasswordResetPayload(auth0ClientId, id, TICKET_TIME_OUT_DURATION);
            String ticket = webClient.post()
                    .uri("/api/v2/tickets/password-change")
                    .header("authorization", "Bearer " + accessToken)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(Map.class).handle((errorBody, sink) -> {
                                sink.error(new RuntimeException(errorBody.get("message").toString()));
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

    private void updateProfile(String userId,UserProfile profile) throws WebClientResponseException{
        String users = webClient.patch()
                .uri("/api/v2/users/" + userId)
                .header("authorization", "Bearer " + accessToken)
                .bodyValue(profile)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).handle((errorBody, sink) -> {
                            sink.error(new RuntimeException("Error updating profile: " + errorBody));
                        })
                )
                .bodyToMono(String.class)
                .block();

    }

    @Override
    public ResponseEntity<String> updateProfile(String userId, String data) {
        accessToken = getAccessToken();
        try {
            UserProfilePayload payload = objectMapper.readValue(data, UserProfilePayload.class);
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

            UserProfile userProfile;
            if (payload.getIsSocial()) {
                userProfile = objectMapper.readValue(payload.getPayload(), SocialUserProfile.class);
                updateProfile(userId,userProfile);
            } else {
                UserProfile usernameUserProfile = objectMapper.readValue(payload.getPayload(),UsernameAuth0UserProfile.class);
                updateProfile(userId, usernameUserProfile);
                UserProfile emailUserProfile = objectMapper.readValue(payload.getPayload(), EmailAuth0UserProfile.class);
                updateProfile(userId,emailUserProfile);

            }


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
    @Override
    public ResponseEntity<String> resendVerificationEmail(String userId){
        accessToken = getAccessToken();
        try {
            SendEmailVerificationPayload payload = new SendEmailVerificationPayload(auth0ClientId, userId);
            webClient.post()
                    .uri("/api/v2/jobs/verification-email")
                    .header("authorization", "Bearer " + accessToken)
                    .bodyValue(payload)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(Map.class).handle((errorBody, sink) -> {
                                sink.error(new RuntimeException(errorBody.get("message").toString()));
                            })
                    )
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok("201");

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

    @Override
    public ResponseEntity<String> getActiveUsers() {
        accessToken = getAccessToken();
        try {
            String numberOfActiveUsers = webClient.get()
                    .uri("/api/v2/stats/active-users")
                    .header("authorization", "Bearer " + accessToken)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(Map.class).handle((errorBody, sink) -> {
                                sink.error(new RuntimeException(errorBody.get("message").toString()));
                            })
                    )
                    .bodyToMono(String.class)
                    .block();

            return ResponseEntity.ok(numberOfActiveUsers);

        } catch (WebClientResponseException e) {
            // Handle specific HTTP error responses here
            return ResponseEntity.status(e.getStatusCode())
                    .body("Failed to get active user: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

}
