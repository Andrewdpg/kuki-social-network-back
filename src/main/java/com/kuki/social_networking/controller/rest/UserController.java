package com.kuki.social_networking.controller.rest;

import com.kuki.social_networking.mapper.UserMapper;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.request.UpdateUserRequest;
import com.kuki.social_networking.response.SimpleUserResponse;
import com.kuki.social_networking.service.interfaces.FollowService;
import com.kuki.social_networking.service.interfaces.UserService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    /**
     * Retrieves a user by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The user with the specified username.
     */
    @GetMapping("/{username}")
    public ResponseEntity<SimpleUserResponse> getUser(@AuthenticatedUser CustomUserDetails userDetails, @PathVariable String username) {
        User user = userService.getUserByUsername(username);
        SimpleUserResponse response = userMapper.toSimpleUserResponse(user);
        response.setFollowed(userDetails != null && user.getFollowers().stream().anyMatch(follow -> follow.getFollower().getUsername().equals(userDetails.getUsername())));
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return a list of User objects representing all users in the system.
     */
    @GetMapping
    public ResponseEntity<List<SimpleUserResponse>> getAllUsers(@AuthenticatedUser CustomUserDetails userDetails) {
        List<User> users = userService.getAllUsers();
        List<SimpleUserResponse> response = users.stream()
                .map((user) ->{
                    SimpleUserResponse simpleUserResponse = userMapper.toSimpleUserResponse(user);
                    simpleUserResponse.setFollowed(userDetails !=null && user.getFollowers().stream().anyMatch(follow -> follow.getFollower().getUsername().equals(userDetails.getUsername())));
                    return simpleUserResponse;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    /**
     * Updates the profile of an existing user.
     *
     * @param userDetails The authenticated user to be updated.
     * @param request     The updated user data.
     * @return The updated user.
     */
    @PutMapping
    public ResponseEntity<SimpleUserResponse> updateUser(@AuthenticatedUser CustomUserDetails userDetails, @RequestBody UpdateUserRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        User updatedUser = userService.updateUserProfile(user, request);
        SimpleUserResponse response = userMapper.toSimpleUserResponse(updatedUser);
        response.setFollowed(userDetails != null && user.getFollowers().stream().anyMatch(follow -> follow.getFollower().getUsername().equals(userDetails.getUsername())));
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of users that match the given search criteria.
     *
     * @param search might be username or full name.
     * @return A list of matching users.
     */
    @DeleteMapping("/profile-picture")
    public ResponseEntity<Void> deleteProfilePicture(@AuthenticatedUser CustomUserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        user.setPhotoUrl("DEFAULT LINK");
        userService.updateUserProfile(user, UpdateUserRequest.builder().build());
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a user from the system.
     *
     * @param userDetails The user to be deleted.
     */
    @PostMapping("/profile-picture")
    public ResponseEntity<SimpleUserResponse> uploadProfilePicture(@AuthenticatedUser CustomUserDetails userDetails, @RequestParam("file") MultipartFile file) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        User updatedUser = userService.uploadProfilePicture(user, file);
        SimpleUserResponse response = userMapper.toSimpleUserResponse(updatedUser);
        response.setFollowed(userDetails != null && user.getFollowers().stream().anyMatch(follow -> follow.getFollower().getUsername().equals(userDetails.getUsername())));
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the current authenticated user's information.
     *
     * @param userDetails The authenticated user's details.
     * @return The authenticated user's information.
     */
    @GetMapping("/me")
    public ResponseEntity<SimpleUserResponse> getCurrentUser(@AuthenticatedUser CustomUserDetails userDetails) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        SimpleUserResponse response = userMapper.toSimpleUserResponse(user);
        response.setFollowed(false);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates the profile of the current authenticated user.
     *
     * @param userDetails The authenticated user to be updated.
     * @param request     The updated user data.
     * @return The updated user.
     */
    @PutMapping("/me")
    public ResponseEntity<SimpleUserResponse> updateCurrentUser(@AuthenticatedUser CustomUserDetails userDetails, @RequestBody UpdateUserRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());
        User updatedUser = userService.updateUserProfile(user, request);
        SimpleUserResponse response = userMapper.toSimpleUserResponse(updatedUser);
        response.setFollowed(false);
        return ResponseEntity.ok(response);
    }
}