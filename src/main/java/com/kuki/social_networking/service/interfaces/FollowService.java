package com.kuki.social_networking.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import com.kuki.social_networking.model.UserFollow;
import com.kuki.social_networking.request.PageableRequest;

/**
 * Interface for managing follow relationships between users in the application.
 */
public interface FollowService {

    /**
     * Follows a user.
     *
     * @param follower The user who is following.
     * @param followedUsername The username of the user being followed.
     */
    void followUser(UserDetails follower, String followedUsername);

    /**
     * Unfollows a user.
     *
     * @param follower The user who is unfollowing.
     * @param followedUsername The username of the user being unfollowed.
     */
    void unfollowUser(UserDetails follower, String followedUsername);

    /**
     * Retrieves the users followed by a specific user.
     *
     * @param username The username of the user.
     * @return A list of users followed by the specified user.
     */
    Page<UserFollow> getFollowedUsers(String username, PageableRequest request);

    /**
     * Retrieves the number of users followed by a specific user.
     *
     * @param username The username of the user.
     * @return The number of users followed by the specified user.
     */
    Integer getFollowedUsersCount(String username);

    /**
     * Retrieves the followers of a specific user.
     *
     * @param username The username of the user.
     * @return A list of users following the specified user.
     */
    Page<UserFollow> getFollowers(String username, PageableRequest request);

    /**
     * Retrieves the number of users following a specific user.
     *
     * @param username The username of the user.
     * @return The number of users following the specified user.
     */
    Integer getFollowersCount(String username);

    /**
     * Checks if a user is following another user.
     *
     * @param username The username of the user.
     * @param followedUsername The username of the user being followed.
     * @return True if the user is following the other user, otherwise false.
     */
    Boolean isFollowing(String username, String followedUsername);
}
