package com.windear.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.windear.app.dto.Auth0LogDTO;
import com.windear.app.dto.SendEmailVerificationPayload;
import com.windear.app.entity.Auth0Log;
import com.windear.app.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class Auth0ManagementServiceImpl implements Auth0ManagementService {
    @Value("${app.client.id}")
    private String auth0ClientId;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final WebClient auth0WebClient;

    private final Auth0AccessTokenService auth0AccessTokenService;

    private final Auth0LogService auth0LogService;

    public Auth0ManagementServiceImpl(@Qualifier("auth0WebClient") WebClient auth0WebClient,
                                      Auth0AccessTokenService auth0AccessTokenService,
                                      Auth0LogService auth0LogService) {
        this.auth0WebClient = auth0WebClient;
        this.auth0AccessTokenService = auth0AccessTokenService;
        this.auth0LogService = auth0LogService;
    }

    @Override
    public String getUsers() {
        return auth0WebClient.get()
                .uri("/api/v2/users?include_totals=true")
                .header("authorization",
                        "Bearer " + auth0AccessTokenService.getAccessToken())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public ResponseEntity<String> deleteUser(String id) {
        try {
            String response = auth0WebClient.delete()
                    .uri("/api/v2/users/" + id)
                    .header("authorization", "Bearer " + auth0AccessTokenService.getAccessToken())
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
                            .header("authorization", "Bearer " + auth0AccessTokenService.getAccessToken())
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
                .header("authorization", "Bearer " + auth0AccessTokenService.getAccessToken())
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

            if (payload.getIsSocial().equals("true")) {
                SocialUserProfile userProfile = objectMapper.readValue(payload.getPayload(), SocialUserProfile.class);
                updateProfile(userId, userProfile);
            } else {
                Auth0UserProfile userProfile = objectMapper.readValue(payload.getPayload(), Auth0UserProfile.class);
                UsernameAuth0UserProfile usernameUserProfile = new UsernameAuth0UserProfile(userProfile.getUsername(), userProfile.getUser_metadata());
                updateProfile(userId, usernameUserProfile);
                EmailAuth0UserProfile emailUserProfile = new EmailAuth0UserProfile(userProfile.getEmail(), userProfile.getUser_metadata());
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
                    .header("authorization", "Bearer " + auth0AccessTokenService.getAccessToken())
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
                    .header("authorization", "Bearer " + auth0AccessTokenService.getAccessToken())
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

    private int findLessRecentLog(Auth0Log[] logs, Auth0Log latestLog) {
        int left = 0;
        int right = logs.length - 1;
        int mid = logs.length - 1;
        while (left <= right) {
            mid = left + (right - left) / 2;
            Auth0Log midLog = logs[mid];

            int comparison = midLog.getDate().compareTo(latestLog.getDate());

            if (comparison > 0) {
                left = mid + 1;
            } else if (comparison < 0) {
                right = mid - 1;
            } else {
                break;
            }
        }

        return mid;
    }

    @Scheduled(cron = "0 30 * * * ?")
    public void getAuth0Logs() {
        try {
            Auth0Log[] logs = auth0WebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/v2/logs")
                            .queryParam("fields", "date,ip,user_agent,user_id,user_name,isMobile,type")
                            .queryParam("include_fields", false)
                            .queryParam("q", "\"s\" or \"ss\" or \"slo\" or \"sdu\" or \"sce\" or \"scu\" or \"scp\" or \"scpr\" or \"fce\" or \"fcu\" or \"fcp\" or \"fcpr\"\n")
                            .build())
                    .header("authorization", "Bearer " + auth0AccessTokenService.getAccessToken())
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(Map.class).handle((errorBody, sink) -> {
                                sink.error(new RuntimeException(errorBody.get("message").toString()));
                            })
                    )
                    .bodyToMono(Auth0Log[].class)
                    .block();
            if (logs == null || logs.length == 0) {
                return;
            }
            Auth0Log latestLog = auth0LogService.getLatestLog();
            int lessRecentLogIndex = findLessRecentLog(logs, latestLog);
            for (int i = lessRecentLogIndex; i >= 0; i--) {
                auth0LogService.addLog(logs[i]);
            }
        } catch (WebClientResponseException e) {
            // Handle specific HTTP error responses here
            throw new RuntimeException("Failed to get logs: " + e.getResponseBodyAsString());
        } catch (Exception e) {
            // Handle any other exceptions
            throw new RuntimeException("Unexpected error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getLogs() {
        Page<Auth0Log> logsPage = auth0LogService.getLogsPage(0, 50);
        List<Auth0LogDTO> logsDTO = logsPage.getContent().stream()
                .map(log -> new Auth0LogDTO(
                        log.getDate(),
                        log.getIp(),
                        log.getUser_agent(),
                        log.getUser_id(),
                        log.getUser_name(),
                        log.isMobile(),
                        log.getType()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(logsDTO);

    }

}
