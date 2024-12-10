package com.kuki.social_networking.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.model.UserStatus;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

// TODO: Remake test
@Disabled
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User user;

    /**
     * Sets up the test environment before each test.
     * Creates a test user and saves it to the database.
     */
    @BeforeEach
    public void setUp() {
        Role role = roleRepository.getReferenceById(1);

        user = User.builder()
                .username("testuser")
                .email("testuser@example.com")
                .fullName("Test User")
                .password("password")
                .registerDate(Timestamp.from(Instant.now()))
                .userStatus(UserStatus.ACTIVE)
                .country("Test Country")
                .roles(List.of(role))
                .build();
        userRepository.save(user);
    }

    /**
     * Tests finding a user by email.
     * Verifies that:
     * 1. A user can be successfully retrieved by their email
     * 2. The retrieved user has the correct username
     */
    @Test
    @Transactional
    public void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("testuser@example.com");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    /**
     * Tests finding users by partial username or full name, ignoring case.
     * Verifies that:
     * 1. Users can be found based on partial matches of username or full name
     * 2. The search is case-insensitive
     * 3. The retrieved user list is not empty and contains the expected user
     */
    @Test
    @Transactional
    public void testFindByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase() {
        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase("test", "user");
        assertFalse(users.isEmpty());
        assertEquals("jhondoe", users.get(0).getUsername());
    }

    /**
     * Tests finding users followed by a specific user.
     * Verifies that:
     * 1. The list of followed users can be retrieved
     * 2. The retrieved list is not null
     * Note: This test assumes UserFollow entities are set up in the database
     */
    @Test
    @Transactional
    public void testFindFollowedByUser() {
        List<User> followedUsers = userRepository.findFollowedByUser("testuser");
        assertNotNull(followedUsers);
    }

    /**
     * Tests finding followers of a specific user.
     * Verifies that:
     * 1. The list of followers can be retrieved
     * 2. The retrieved list is not null
     * Note: This test assumes UserFollow entities are set up in the database
     */
    @Test
    @Transactional
    public void testFindFollowersOfUser() {
        List<User> followers = userRepository.findFollowersOfUser("testuser");
        assertNotNull(followers);
    }

    /**
     * Tests saving and retrieving a user.
     * Verifies that:
     * 1. A user can be successfully saved to the database
     * 2. The saved user can be retrieved by their username
     * 3. The retrieved user has the correct email
     */
    @Test
    @Transactional
    public void testSaveAndRetrieveUser() {
        Optional<User> foundUser = userRepository.findById("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
    }

    /**
     * Tests updating a user's information.
     * Verifies that:
     * 1. A user's information can be updated
     * 2. The updated information is correctly persisted in the database
     */
    @Test
    @Transactional
    public void testUpdateUser() {
        user.setFullName("Updated User");
        userRepository.save(user);
        Optional<User> updatedUser = userRepository.findById("testuser");
        assertTrue(updatedUser.isPresent());
        assertEquals("Updated User", updatedUser.get().getFullName());
    }

    /**
     * Tests deleting a user.
     * Verifies that:
     * 1. A user can be successfully deleted from the database
     * 2. The deleted user cannot be found after deletion
     */
    @Test
    @Transactional
    public void testDeleteUser() {
        userRepository.delete(user);
        Optional<User> deletedUser = userRepository.findById("testuser");
        assertFalse(deletedUser.isPresent());
    }
}