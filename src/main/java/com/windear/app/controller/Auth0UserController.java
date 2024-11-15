package com.windear.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.windear.app.model.*;
import com.windear.app.service.Auth0ManagementService;
import com.windear.app.service.ExternalBookService;
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
    Auth0ManagementService auth0ManagementService;

    @Autowired
    public Auth0UserController(Auth0ManagementService auth0ManagementService) {
        this.auth0ManagementService = auth0ManagementService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<String> getUserLists() {
        String users = auth0ManagementService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/user/profile/{userId}")
    public ResponseEntity<String> updateProfile(@RequestBody String data, @PathVariable String userId) {
        return auth0ManagementService.updateProfile(userId, data);
    }

    @GetMapping("/user/{userId}/reset-password")
    public ResponseEntity<String> resetPassword(@PathVariable String userId) {
        return auth0ManagementService.resetPassword(userId);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        return auth0ManagementService.deleteUser(userId);
    }
}
