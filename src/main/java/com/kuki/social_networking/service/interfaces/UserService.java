package com.kuki.social_networking.service.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.request.RegisterUserRequest;
import com.kuki.social_networking.request.UpdateUserRequest;

/**
 * Interface for managing users in the application.
 */
public interface UserService {

    /**
     * Registers a new user in the system.
     *
     * @param request The user data to be registered.
     * @return The registered user with updated information (e.g., ID, timestamps).
     */
    User registerUser(RegisterUserRequest request);

    /**
     * Updates the profile of an existing user.
     *
     * @param user The authenticated user to be updated.
     * @param request The updated user data.
     * @return The updated user.
     */
    User updateUserProfile(User user, UpdateUserRequest request);

    /**
     * Retrieves a list of users that match the given search criteria.
     *
     * @param search might be username or full name.
     * @return A list of matching users.
     */
    List<User> searchUsers(String search);

    /**
     * Deletes a user from the system.
     *
     * @param user The user to be deleted.
     */
    void deleteUser(User user);

    /**
     * Retrieves a list of all users.
     *
     * @return a list of User objects representing all users in the system.
     */
    List<User> getAllUsers();

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The user with the specified username.
     */
    User getUserByUsername(String username);

    User uploadProfilePicture(User user, MultipartFile file);
    
}
