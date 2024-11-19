package com.windear.app.service;

import com.windear.app.entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Auth0ManagementService {
    String getUsers();
    ResponseEntity<String> deleteUser(String id);
    ResponseEntity<String> resetPassword(String id);
    ResponseEntity<String> updateProfile(String userId, String data);
    ResponseEntity<String> resendVerificationEmail(String userId);
    ResponseEntity<String> getActiveUsers();
}
