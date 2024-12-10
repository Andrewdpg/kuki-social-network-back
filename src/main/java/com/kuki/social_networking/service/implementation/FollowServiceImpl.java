package com.kuki.social_networking.service.implementation;

import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kuki.social_networking.exception.AlreadyFollowingException;
import com.kuki.social_networking.exception.NotFollowingException;
import com.kuki.social_networking.exception.UserNotFoundException;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.UserFollow;
import com.kuki.social_networking.repository.UserFollowRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.service.interfaces.FollowService;
import com.kuki.social_networking.service.interfaces.NotificationService;
import static com.kuki.social_networking.util.EntitySpecs.getPageable;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the follow service.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FollowServiceImpl implements FollowService {

    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    /**
     * Follows a user.
     *
     * @param follower The user who is following.
     * @param followedUsername The username of the user being followed.
     */
    @Override
    public void followUser(UserDetails follower, String followedUsername) {
        if (follower.getUsername().equals(followedUsername)) {
            throw new IllegalArgumentException("Users cannot follow themselves.");
        }

        User followed = userRepository.findByUsername(followedUsername)
            .orElseThrow(() -> new UserNotFoundException("Followed user not found: " + followedUsername));

        User followerUser = userRepository.findByUsername(follower.getUsername())
            .orElseThrow(() -> new UserNotFoundException("Follower user not found: " + follower.getUsername()));

        if(userFollowRepository.existsByFollowerAndFollowed(followerUser, followed)) {
            throw new AlreadyFollowingException("You are already following this user.");
        }

        UserFollow userFollow = UserFollow.builder()
            .follower(followerUser)
            .followed(followed)
            .creationDate(Timestamp.from(Instant.now()))
            .build();

        userFollowRepository.save(userFollow);

        String notificationContent = follower.getUsername() + " has started following you.";
        notificationService.sendNotification(followed.getUsername(), notificationContent, follower.getUsername());
    }

    /**
     * Unfollows a user.
     *
     * @param follower The user who is unfollowing.
     * @param followedUsername The username of the user being unfollowed.
     */
    @Override
    public void unfollowUser(UserDetails follower, String followedUsername) {
        User followed = userRepository.findByUsername(followedUsername)
            .orElseThrow(() -> new UserNotFoundException("Followed user not found: " + followedUsername));

        User followerUser = userRepository.findByUsername(follower.getUsername())
            .orElseThrow(() -> new UserNotFoundException("Follower user not found: " + follower.getUsername()));

        UserFollow userFollow = userFollowRepository.findByFollowerAndFollowed(followerUser, followed)
            .orElseThrow(() -> new NotFollowingException("You are not following this user."));

        userFollowRepository.delete(userFollow);
    }

    /**
     * Retrieves the users followed by a specific user.
     *
     * @param username The username of the user.
     * @return A list of users followed by the specified user.
     */
    @Override
    public Page<UserFollow> getFollowedUsers(String username, PageableRequest request) {
        if(!userRepository.existsById(username)) {
            throw new UserNotFoundException("User not found: " + username);
        }

        return userRepository.findFollowedByUser(username, request.pageable());
    }

    /**
     * Retrieves the number of users followed by a specific user (Seguidos).
     *
     * @param username The username of the user.
     * @return The number of users followed by the specified user.
     */
    @Override
    public Integer getFollowedUsersCount(String username) {
        if(!userRepository.existsById(username)) {
            throw new UserNotFoundException("User not found: " + username);
        }

        return userRepository.countUsersFollowers(username);
    }

    /**
     * Retrieves the followers of a specific user.
     *
     * @param username The username of the user.
     * @return A list of users following the specified user.
     */
    @Override
    public Page<UserFollow> getFollowers(String username, PageableRequest request) {
        if(!userRepository.existsById(username)) {
            throw new UserNotFoundException("User not found: " + username);
        }

        return userRepository.findFollowersOfUser(username, request.pageable());
    }

    /**
     * Retrieves the number of users following a specific user (Seguidores).
     *
     * @param username The username of the user.
     * @return The number of users following the specified user.
     */
    @Override
    public Integer getFollowersCount(String username) {
        if(!userRepository.existsById(username)) {
            throw new UserNotFoundException("User not found: " + username);
        }

        return userRepository.countUsersFollowedByUser(username);
    }


    @Override
    public Boolean isFollowing(String username, String followedUsername) {
        if(!userRepository.existsById(username)) {
            throw new UserNotFoundException("User not found: " + username);
        }

        if(!userRepository.existsById(followedUsername)) {
            throw new UserNotFoundException("User not found: " + followedUsername);
        }

        return userRepository.isFollowing(username, followedUsername);
    }


}