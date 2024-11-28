package com.windear.app.service;

import com.windear.app.entity.Auth0Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Auth0ManagementService {
    /**
     * Retrieves a list of users.
     * @return a list of users
     */
    String getUsers();

    /**
     * Deletes a user by their ID.
     * @param id the ID of the user to delete
     * @return a ResponseEntity with the result of the deletion
     */
    ResponseEntity<String> deleteUser(String id);

    /**
     * Resets the password for a user by their ID.
     * @param id the ID of the user whose password is to be reset
     * @return a ResponseEntity with the result of the password reset
     */
    ResponseEntity<String> resetPassword(String id);

    /**
     * Updates the profile of a user by their ID.
     * @param userId the ID of the user whose profile is to be updated
     * @param data the new profile data
     * @return a ResponseEntity with the result of the profile update
     */
    ResponseEntity<String> updateProfile(String userId, String data);

    /**
     * Resends the verification email to a user by their ID.
     * @param userId the ID of the user to resend the verification email to
     * @return a ResponseEntity with the result of the email resend
     */
    ResponseEntity<String> resendVerificationEmail(String userId);

    /**
     * Retrieves a list of active users.
     * @return a ResponseEntity containing the list of active users
     */
    ResponseEntity<String> getActiveUsers();

    /**
     * Retrieves a list of logs.
     * @return a ResponseEntity containing the logs
     */
    ResponseEntity<?> getLogs();
}
