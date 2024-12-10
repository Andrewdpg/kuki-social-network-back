package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.service.implementation.CustomUserDetails;

/**
 * Interface for authenticating users in the application.
 */
public interface AuthService {

    /**
     * Authenticates a user based on email and password.
     *
     * @param username The user's nickname.
     * @param password The user's password.
     * @return JWT token.
     */
    String authenticateUser(String username, String password);

    /**
     * Retrieves the currently authenticated user.
     *
     * @return The authenticated user.
     */
    CustomUserDetails getAuthenticatedUser();

    /**
     * Logs out the currently authenticated user.
     */
    void logout();
}
