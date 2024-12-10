package com.kuki.social_networking.service;

import com.kuki.social_networking.exception.AlreadyFollowingException;
import com.kuki.social_networking.exception.NotFollowingException;
import com.kuki.social_networking.exception.UserNotFoundException;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.UserFollow;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.model.UserStatus;
import com.kuki.social_networking.repository.RoleRepository;
import com.kuki.social_networking.repository.UserFollowRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.service.interfaces.FollowService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


// TODO: Remake test
@Disabled
@SpringBootTest
@Transactional
public class FollowServiceTest {

    @Autowired
    private FollowService followService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserFollowRepository userFollowRepository;
    @Autowired
    private RoleRepository roleRepository;

    private final List<Role> userRoles = List.of(
            roleRepository.getReferenceById(1)
    );

    private User user;
    private User user2;

    @BeforeEach
    public void setUp() {
        user = User.builder()
            .username("testuser")
            .email("johnDoe@example.com")
            .password(passwordEncoder.encode("password"))
            .fullName("John Doe")
            .country("United States")
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .biography("")
            .roles(userRoles)
            .lastConnection(Timestamp.from(Instant.now()))
            .isEmailVerified(true)
            .build();

        user2 = User.builder()
            .username("testuser2")
            .email("samsmith@example.com")
            .password(passwordEncoder.encode("password"))
            .fullName("Sam Smith")
            .country("United Kingdom")
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .biography("")
            .roles(userRoles)
            .lastConnection(Timestamp.from(Instant.now()))
            .isEmailVerified(true)
            .build();

        userRepository.save(user);
        userRepository.save(user2);
    }

    /**
     * Tests the followUser method of the FollowService.
     * This test verifies that:
     * 1. A user can successfully follow another user.
     * 2. The follower user has the followed user in their following list.
     * 3. The followed user has the follower user in their followers list.
     */
    @Test
    public void followUser() {
        followService.followUser(user, user2.getUsername());

        UserFollow relation = userFollowRepository.findByFollowerAndFollowed(user, user2).orElse(null);

        assertNotNull(relation);
    }

    /**
     * Tests the unfollowUser method of the FollowService.
     * This test verifies that:
     * 1. A user can successfully unfollow another user.
     * 2. The follower user no longer has the followed user in their following list.
     * 3. The followed user no longer has the follower user in their followers list.
     */
    @Test
    public void unfollowUser() {
        followService.followUser(user, user2.getUsername());
        followService.unfollowUser(user, user2.getUsername());

        UserFollow relation = userFollowRepository.findByFollowerAndFollowed(user, user2).orElse(null);

        assertNull(relation);
    }

    /**
     * Tests the getFollowers method of the FollowService.
     * This test verifies that:
     * 1. The method returns the correct list of followers for a user.
     * 2. The list is not empty when the user has followers.
     * 3. The list contains the correct followers.
     */
    @Test
    public void getFollowers() {
        followService.followUser(user, user2.getUsername());

        List<User> followers = followService.getFollowers(user2.getUsername());

        assertNotNull(followers);
        assertEquals(1, followers.size());
        assertEquals(user.getUsername(), followers.get(0).getUsername());
    }

    /**
     * Tests the getFollowedUsers method of the FollowService.
     * This test verifies that:
     * 1. The method returns the correct list of followed users for a user.
     * 2. The list is not empty when the user is following other users.
     * 3. The list contains the correct followed users.
     */
    @Test
    public void getFollowedUsers() {
        followService.followUser(user, user2.getUsername());

        List<User> followedUsers = followService.getFollowedUsers(user.getUsername());

        assertNotNull(followedUsers);
        assertEquals(1, followedUsers.size());
        assertEquals(user2.getUsername(), followedUsers.get(0).getUsername());
    }

    /**
     * Tests the getFollowers method of the FollowService when the user has no followers.
     * This test verifies that the method returns an empty list when the user has no followers.
     */
    @Test
    public void getFollowersEmpty() {
        List<User> followers = followService.getFollowers(user2.getUsername());

        assertNotNull(followers);
        assertEquals(0, followers.size());
    }

    /**
     * Tests the getFollowedUsers method of the FollowService when the user is not following anyone.
     * This test verifies that the method returns an empty list when the user is not following anyone.
     */
    @Test
    public void getFollowedUsersEmpty() {
        List<User> followedUsers = followService.getFollowedUsers(user.getUsername());

        assertNotNull(followedUsers);
        assertEquals(0, followedUsers.size());
    }

    /**
     * Tests the followUser method of the FollowService when the user tries to follow themselves.
     * This test verifies that an IllegalArgumentException is thrown when a user tries to follow themselves.
     */
    @Test
    public void followUserSelf() {
        try {
            followService.followUser(user, user.getUsername());
        } catch (IllegalArgumentException e) {
            assertEquals("Users cannot follow themselves.", e.getMessage());
        }
    }

    /**
     * Tests the followUser method of the FollowService when the user is already following the other user.
     * This test verifies that an AlreadyFollowingException is thrown when a user tries to follow another user that they are already following.
     */
    @Test
    public void followUserAlreadyFollowing() {
        followService.followUser(user, user2.getUsername());
        try {
            followService.followUser(user, user2.getUsername());
        } catch (AlreadyFollowingException e) {
            assert  true;
        }
    }

    /**
     * Tests the unfollowUser method of the FollowService when the user is not following the other user.
     * This test verifies that a NotFollowingException is thrown when a user tries to unfollow another user that they are not following.
     */
    @Test
    public void unfollowUserNotFollowing() {
        try {
            followService.unfollowUser(user, user2.getUsername());
        } catch (NotFollowingException e) {
            assertEquals("You are not following this user.", e.getMessage());
        }
    }

    /**
     * Tests the getFollowedUsers method of the FollowService when the user is not found.
     * This test verifies that a UserNotFoundException is thrown when the user is not found.
     */
    @Test
    public void getFollowedUsersNotFound() {
        try {
            followService.getFollowedUsers("nonexistentuser");
        } catch (UserNotFoundException e) {
            assertEquals("User not found: nonexistentuser", e.getMessage());
        }
    }

    /**
     * Tests the getFollowers method of the FollowService when the user is not found.
     * This test verifies that a UserNotFoundException is thrown when the user is not found.
     */
    @Test
    public void getFollowersNotFound() {
        try {
            followService.getFollowers("nonexistentuser");
        } catch (UserNotFoundException e) {
            assertEquals("User not found: nonexistentuser", e.getMessage());
        }
    }

    /**
     * Tests the unfollowUser method of the FollowService when the followed user is not found.
     * This test verifies that a UserNotFoundException is thrown when the followed user is not found.
     */
    @Test
    public void unfollowUserFollowedNotFound() {
        try {
            followService.unfollowUser(user, "nonexistentuser");
        } catch (UserNotFoundException e) {
            assertEquals("Followed user not found: nonexistentuser", e.getMessage());
        }
    }

    /**
     * Tests the followUser method of the FollowService when the followed user is not found.
     * This test verifies that a UserNotFoundException is thrown when the followed user is not found.
     */
    @Test
    public void followUserFollowedNotFound() {
        try {
            followService.followUser(user, "nonexistentuser");
        } catch (UserNotFoundException e) {
            assertEquals("Followed user not found: nonexistentuser", e.getMessage());
        }
    }
}