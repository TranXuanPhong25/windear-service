package com.windear.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.windear.app.dto.SendEmailVerificationPayload;
import com.windear.app.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class Auth0ManagementServiceImpl implements Auth0ManagementService {
    @Value("${app.client.id}")
    private String auth0ClientId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WebClient auth0WebClient;

    private final Auth0AccessToken auth0AccessToken;

    public Auth0ManagementServiceImpl(@Qualifier("auth0WebClient") WebClient auth0WebClient) {
        this.auth0WebClient = auth0WebClient;
        this.auth0AccessToken = new Auth0AccessToken(auth0WebClient);
    }

    @Override
    public String getUsers() {
        return auth0WebClient.get()
                .uri("/api/v2/users?include_totals=true")
                .header("authorization", "Bearer " + auth0AccessToken.getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public ResponseEntity<String> deleteUser(String id) {
        try {
            String response = auth0WebClient.delete()
                    .uri("/api/v2/users/" + id)
                    .header("authorization", "Bearer " + auth0AccessToken.getAccessToken())
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
        try {
            int TICKET_TIME_OUT_DURATION = 300;
            PasswordResetPayload payload = new PasswordResetPayload(auth0ClientId, id, TICKET_TIME_OUT_DURATION);
            String ticket =
                    auth0WebClient.post()
                            .uri("/api/v2/tickets/password-change")
                            .header("authorization", "Bearer " + auth0AccessToken.getAccessToken())
                            .bodyValue(payload)
                            .retrieve()
                            .onStatus(
                                    status -> status.is4xxClientError() || status.is5xxServerError(),
                                    clientResponse -> clientResponse.
                                            bodyToMono(Map.class)
                                            .handle((errorBody, sink) -> sink.error(new RuntimeException(errorBody.get("message").toString())))
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

    private void updateProfile(String userId, UserProfile profile) throws WebClientResponseException {
        auth0WebClient.patch()
                .uri("/api/v2/users/" + userId)
                .header("authorization", "Bearer " + auth0AccessToken.getAccessToken())
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
        try {
            UserProfilePayload payload = objectMapper.readValue(data, UserProfilePayload.class);
            objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

            UserProfile userProfile;
            if (payload.getIsSocial()) {
                userProfile = objectMapper.readValue(payload.getPayload(), SocialUserProfile.class);
                updateProfile(userId, userProfile);
            } else {
                UserProfile usernameUserProfile = objectMapper.readValue(payload.getPayload(), UsernameAuth0UserProfile.class);
                updateProfile(userId, usernameUserProfile);
                UserProfile emailUserProfile = objectMapper.readValue(payload.getPayload(), EmailAuth0UserProfile.class);
                updateProfile(userId, emailUserProfile);

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
    public ResponseEntity<String> resendVerificationEmail(String userId) {
        try {
            SendEmailVerificationPayload payload = new SendEmailVerificationPayload(auth0ClientId, userId);
            auth0WebClient.post()
                    .uri("/api/v2/jobs/verification-email")
                    .header("authorization", "Bearer " + auth0AccessToken.getAccessToken())
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
        try {
            String numberOfActiveUsers = auth0WebClient.get()
                    .uri("/api/v2/stats/active-users")
                    .header("authorization", "Bearer " + auth0AccessToken.getAccessToken())
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
