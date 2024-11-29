package com.windear.app.controller;

import com.windear.app.entity.Auth0Log;
import com.windear.app.service.Auth0ManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth0")
public class Auth0Controller {
    Auth0ManagementService auth0ManagementService;

    @Autowired
    public Auth0Controller(Auth0ManagementService auth0ManagementService) {
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

    @PostMapping("/user/resend-verification-email")
    public ResponseEntity<String> resendVerificationEmail(@RequestBody Map<String, String> data) {
        return auth0ManagementService.resendVerificationEmail(data.get("data"));
    }

    @GetMapping("/stats/active-users")
    public ResponseEntity<String> getsActiveUsers() {
        return auth0ManagementService.getActiveUsers();
    }

    @GetMapping("/logs")
    public ResponseEntity<?> getLogs() {
        return auth0ManagementService.getLogs();
    }
}
