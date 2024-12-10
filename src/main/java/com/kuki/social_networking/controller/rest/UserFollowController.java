package com.kuki.social_networking.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kuki.social_networking.exception.AlreadyFollowingException;
import com.kuki.social_networking.exception.NotFollowingException;
import com.kuki.social_networking.exception.UserNotFoundException;
import com.kuki.social_networking.mapper.UserFollowMapper;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.request.UserFollowRequest;
import com.kuki.social_networking.response.UserFollowResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.FollowService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

/**
 * Controller for managing user follow relationships.
 */
@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class UserFollowController {

    private final FollowService followService;
    private final UserFollowMapper userFollowMapper;

    /**
     * Endpoint to follow a user.
     *
     * @param follower The user who is following.
     * @param request The follow request containing the username of the user to be followed.
     * @return A response entity with status 200 (OK).
     */
    @PostMapping
    public ResponseEntity<Void> followUser(@AuthenticatedUser CustomUserDetails follower, @RequestBody UserFollowRequest request) {
        followService.followUser(follower, request.getFollowedUsername());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to unfollow a user.
     *
     * @param follower The user who is unfollowing.
     * @param request The unfollow request containing the username of the user to be unfollowed.
     * @return A response entity with status 200 (OK).
     */
    @DeleteMapping
    public ResponseEntity<Void> unfollowUser(@AuthenticatedUser CustomUserDetails follower, @ModelAttribute UserFollowRequest request) {
        followService.unfollowUser(follower, request.getFollowedUsername());
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to get the users followed by a specific user.
     *
     * @param username The username of the user.
     * @param pageableRequest The pagination request.
     * @return A response entity containing a page of followed users.
     */
    @GetMapping("/following")
    public ResponseEntity<Page<UserFollowResponse>> getFollowedUsers(@RequestParam String username, PageableRequest pageableRequest) {
        Page<UserFollowResponse> followedUsers = followService.getFollowedUsers(username, pageableRequest)
                .map(userFollowMapper::toUserFollowResponse);
        return ResponseEntity.ok(followedUsers);
    }

    /**
     * Endpoint to get the number of users followed by a specific user (Seguidos).
     *
     * @param username The username of the user.
     * @return A response entity containing the number of users followed by the specified user.
     */
    @GetMapping("/following/count")
    public ResponseEntity<Integer> getFollowingUsersCount(@RequestParam String username) {
        Integer count = followService.getFollowedUsersCount(username);
        return ResponseEntity.ok(count);
    }

    /**
     * Endpoint to get the followers of a specific user.
     *
     * @param username The username of the user.
     * @param pageableRequest The pagination request.
     * @return A response entity containing a page of followers.
     */
    @GetMapping("/followers")
    public ResponseEntity<Page<UserFollowResponse>> getFollowers(@RequestParam String username, PageableRequest pageableRequest) {
        Page<UserFollowResponse> followers = followService.getFollowers(username, pageableRequest)
                .map(userFollowMapper::toUserFollowResponse);
        return ResponseEntity.ok(followers);
    }

    /**
     * Endpoint to get the number of users following a specific user (Seguidores).
     *
     * @param username The username of the user.
     * @return A response entity containing the number of users following the specified user.
     */
    @GetMapping("/followers/count")
    public ResponseEntity<Integer> getFollowersCount(@RequestParam String username) {
        Integer count = followService.getFollowersCount(username);
        return ResponseEntity.ok(count);
    }

    /**
     * Endpoint to check if a user is following another user.
     *
     * @param username The username of the user.
     * @param followedUsername The username of the user being followed.
     * @return A response entity containing a boolean indicating if the user is following the other user.
     */
    @GetMapping("/is-following")
    public ResponseEntity<Boolean> isFollowing(@RequestParam String username, @RequestParam String followedUsername) {
        Boolean isFollowing = followService.isFollowing(username, followedUsername);
        return ResponseEntity.ok(isFollowing);
    }

    // Exception Handlers
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(AlreadyFollowingException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleAlreadyFollowingException(AlreadyFollowingException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(NotFollowingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleNotFollowingException(NotFollowingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}