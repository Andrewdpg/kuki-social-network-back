package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.kuki.social_networking.repository.RoleRepository;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    private Validator validator;

    @Mock
    private List<Role> mockRoles;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test the creation of a User object and verifies that all fields are correctly set.
     */
    @Test
    public void testUserCreation() {
        User user = User.builder()
                .username("johndoe")
                .email("john@example.com")
                .fullName("John Doe")
                .password("password123")
                .country("USA")
                .registerDate(Timestamp.from(Instant.now()))
                .roles(mockRoles)
                .build();

        assertNotNull(user);
        assertEquals("johndoe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("John Doe", user.getFullName());
        assertEquals("password123", user.getPassword());
        assertEquals("USA", user.getCountry());
        assertEquals(UserStatus.ACTIVE, user.getUserStatus());
        assertEquals(mockRoles, user.getRoles());
    }

    /**
     * Validates a User object with missing mandatory fields, expecting validation errors.
     * Then, corrects the fields and revalidates, expecting no validation errors.
     */
    @Test
    public void testUserValidation() {
        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertFalse(violations.isEmpty());

        user.setUsername("johndoe");
        user.setEmail("john@example.com");
        user.setFullName("John Doe");
        user.setPassword("password123");
        user.setCountry("USA");
        user.setRegisterDate(Timestamp.from(Instant.now()));
        user.setRoles(mockRoles);

        violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that the User object's email field is properly validated to ensure it is a valid email format.
     */
    @Test
    public void testUserEmailValidation() {
        User user = User.builder()
                .username("johndoe")
                .email("invalid-email")
                .fullName("John Doe")
                .password("password123")
                .country("USA")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El correo electrónico debe ser válido")));
    }

    /**
     * Tests that the User object's biography field cannot exceed 500 characters.
     */
    @Test
    public void testUserBiographyLength() {
        User user = User.builder()
                .username("johndoe")
                .email("john@example.com")
                .fullName("John Doe")
                .password("password123")
                .country("USA")
                .biography("a".repeat(501))
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("The biography cannot exceed 500 characters")));
    }

    /**
     * Tests that the User object's default values are correctly set.
     */
    @Test
    public void testUserDefaultValues() {
        User user = new User();
        assertEquals("DEFAULT LINK", user.getPhotoUrl());
        assertEquals("", user.getBiography());
        assertTrue(user.getSocialMedia().isEmpty());
        assertEquals(UserStatus.ACTIVE, user.getUserStatus());
    }
}
