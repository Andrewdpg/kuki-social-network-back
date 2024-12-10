package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class NotificationTest {

    private Validator validator;

    @Mock
    private User mockUser;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    /**
     * Test the creation of a Notification object and verifies that all fields are correctly set.
     */
    @Test
    public void testNotificationCreation() {
        Notification notification = Notification.builder()
                .id(UUID.randomUUID())
                .content("New recipe added")
                .isRead(false)
                .creationDate(Timestamp.from(Instant.now()))
                .url("/recipes/123")
                .notificationOwner(mockUser)
                .build();

        assertNotNull(notification);
        assertNotNull(notification.getId());
        assertEquals("New recipe added", notification.getContent());
        assertFalse(notification.isRead());
        assertNotNull(notification.getCreationDate());
        assertEquals("/recipes/123", notification.getUrl());
        assertEquals(mockUser, notification.getNotificationOwner());
    }

    /**
     * Validates a Notification object with missing mandatory fields, expecting validation errors.
     * Then, corrects the fields and revalidates, expecting no validation errors.
     */
    @Test
    public void testNotificationValidation() {
        Notification notification = new Notification();
        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertFalse(violations.isEmpty());

        notification.setContent("New recipe added");
        notification.setCreationDate(Timestamp.from(Instant.now()));
        notification.setNotificationOwner(mockUser);
        violations = validator.validate(notification);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a Notification object's content cannot be blank and that the appropriate validation
     * error message is generated.
     */
    @Test
    public void testNotificationContentNotBlank() {
        Notification notification = Notification.builder()
                .content("")
                .creationDate(Timestamp.from(Instant.now()))
                .notificationOwner(mockUser)
                .build();

        Set<ConstraintViolation<Notification>> violations = validator.validate(notification);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Notification content is mandatory")));
    }

    /**
     * Tests that the default value for a Notification's isRead field is false.
     */
    @Test
    public void testNotificationIsReadDefaultValue() {
        Notification notification = new Notification();
        assertFalse(notification.isRead());
    }
}
