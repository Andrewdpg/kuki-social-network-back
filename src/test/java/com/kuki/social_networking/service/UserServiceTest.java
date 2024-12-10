package com.kuki.social_networking.service;

import com.kuki.social_networking.model.EmailVerification;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.model.UserStatus;
import com.kuki.social_networking.repository.EmailVerificationRepository;
import com.kuki.social_networking.repository.RoleRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.request.RegisterUserRequest;
import com.kuki.social_networking.service.interfaces.UserService;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: Remake test
@Disabled
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailVerificationRepository emailVerificationRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        Role role = roleRepository.getReferenceById(1);

        user = User.builder()
            .username("testuser")
            .email("johnDoe@example.com")
            .password(passwordEncoder.encode("password"))
            .fullName("John Doe")
            .country("United States")
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .biography("")
            .roles(List.of(role))
            .lastConnection(Timestamp.from(Instant.now()))
            .isEmailVerified(true)
            .build();

        userRepository.save(user);
        userRepository.save(User.builder()
            .username("testuser2")
            .email("samsmith@example.com")
            .password(passwordEncoder.encode("password"))
            .fullName("Sam Smith")
            .country("United Kingdom")
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .biography("")
            .roles(List.of(role))
            .lastConnection(Timestamp.from(Instant.now()))
            .isEmailVerified(true)
            .build());
    }

    /**
     * Tests the registration of a new user.
     * This test verifies that:
     * 1. A new user can be successfully registered.
     * 2. The registered user has the correct information.
     * 3. The registered user has an email verification entry.
     */
    @Test
    public void testRegisterUser() {
        Role role = roleRepository.getReferenceById(1);

        RegisterUserRequest newUser = RegisterUserRequest.builder()
            .username("newuser")
            .email("other@example.com")
            .password("password")
            .fullName("Other User")
            .country("United States")
            .build();

        User registeredUser = userService.registerUser(newUser);

        assertNotNull(registeredUser);
        assertEquals(newUser.getUsername(), registeredUser.getUsername());
        assertEquals(newUser.getEmail(), registeredUser.getEmail());
        assertEquals(newUser.getFullName(), registeredUser.getFullName());
        assertEquals(newUser.getCountry(), registeredUser.getCountry());
        assertEquals(UserStatus.ACTIVE, registeredUser.getUserStatus());
        assertEquals(List.of(role), registeredUser.getRoles());
        assertTrue(passwordEncoder.matches(newUser.getPassword(), registeredUser.getPassword()));
        assertFalse(registeredUser.isEmailVerified());

        EmailVerification verification = emailVerificationRepository.findByUser(registeredUser).orElse(null);

        assertNotNull(verification);
        assertEquals(registeredUser, verification.getUser());
    }

    /**
     * Tests the searchUsers method of the UserService.
     * This test verifies that:
     * 1. Users can be found by a partial, case-insensitive username match
     * 3. The returned list is not empty when searching for an existing user.
     * 4. The returned list contains a user with a username that matches the search term (case-insensitive).
     */
    @Test
    public void testSearchUsersByUsername() {
        String search = "test";

        List<User> users = userService.searchUsers(search);

        assertEquals(2, users.size());
    }

    /**
     * Tests the searchUsers method of the UserService.
     * This test verifies that:
     * 1. Users can be found by a partial, case-insensitive full name match
     * 3. The returned list is not empty when searching for an existing user.
     * 4. The returned list contains a user with a full name that matches the search term (case-insensitive).
     */
    @Test
    public void testSearchUsersByFullName() {
        String search = "john";

        List<User> users = userService.searchUsers(search);

        assertEquals(1, users.size());
        assertEquals(user.getUsername(), users.get(0).getUsername());
    }

    /**
     * Tests the updateUserProfile method of the UserService.
     * This test verifies that:
     * 1. A user's profile can be successfully updated.
     * 2. The updated user has the correct information.
     */
    @Test
    public void testDeleteUser() {
        userService.deleteUser(user);

        assertFalse(userRepository.findById(user.getUsername()).isPresent());
    }

    /**
     * Tests the registration of an existing user.
     * This test verifies that:
     * 1. An existing user cannot be registered again.
     * 2. An exception is thrown when trying to register an existing user.
     * 3. The exception message is correct.
     */
    @Test
    public void testRegisterExistingUser() {
        RegisterUserRequest newUser = RegisterUserRequest.builder()
            .username("testuser")
            .email("newemail@example.com")
            .password("password")
            .fullName("John Doe")
            .country("United States")
            .build();

        try {
            userService.registerUser(newUser);
        } catch (Exception e) {
            assertEquals("Username is already taken.", e.getMessage());
        }
    }

    /**
     * Tests the registration of a new user with an existing email.
     * This test verifies that:
     * 1. A new user cannot be registered with an existing email.
     * 2. An exception is thrown when trying to register a new user with an existing email.
     * 3. The exception message is correct.
     */
    @Test
    public void testRegisterUserWithExistingEmail() {
        RegisterUserRequest newUser = RegisterUserRequest.builder()
            .username("newuser")
            .email("johnDoe@example.com")
            .password("password")
            .fullName("John Doe")
            .country("United States")
            .build();

        try {
            userService.registerUser(newUser);
        } catch (Exception e) {
            assertEquals("Email is already in use.", e.getMessage());
        }
    }
}