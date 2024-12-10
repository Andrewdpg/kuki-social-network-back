package com.kuki.social_networking.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kuki.social_networking.model.Notification;
import com.kuki.social_networking.model.User;

// TODO: Remake test
@Disabled
@SpringBootTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    /**
     * Sets up the test environment before each test.
     * Creates a test user and saves it to the database.
     */
    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("testuser")
                .password("password")
                .email("testuser@example.com")
                .country("Some Country")
                .fullName("Test User")
                .build();
        user = userRepository.save(user); 
    }

    /**
     * Tests the creation of a new notification.
     * Verifies that:
     * 1. The notification is successfully saved
     * 2. The saved notification has a non-null ID
     * 3. The content is correctly set
     * 4. The notification is initially unread
     * 5. The notification owner is correctly set
     */
    @Test
    @Transactional
    public void testCreateNotification() {
        Notification notification = Notification.builder()
                .content("Test Notification")
                .creationDate(Timestamp.from(Instant.now()))
                .notificationOwner(user)
                .build();
        notification = notificationRepository.save(notification);

        assertNotNull(notification);
        assertNotNull(notification.getId());
        assertEquals("Test Notification", notification.getContent());
        assertFalse(notification.isRead());
        assertEquals(user, notification.getNotificationOwner());
    }

    /**
     * Tests the deletion of a notification.
     * Verifies that:
     * 1. A notification can be successfully deleted
     * 2. The deleted notification cannot be found in the database
     */
    @Test
    @Transactional
    public void testDeleteNotification() {
        Notification notification = Notification.builder()
                .content("Notification to be deleted")
                .creationDate(Timestamp.from(Instant.now()))
                .notificationOwner(user)
                .build();
        notification = notificationRepository.save(notification);

        UUID notificationId = notification.getId();
        notificationRepository.delete(notification);

        Optional deletedNotification = notificationRepository.findById(notificationId);
        assertTrue(deletedNotification.isEmpty());
    }

    /**
     * Tests updating the read status of a notification.
     * Verifies that:
     * 1. The read status can be successfully updated
     * 2. The updated status is persisted in the database
     */
    @Test
    @Transactional
    public void testUpdateNotificationReadStatus() {
        Notification notification = Notification.builder()
                .content("Notification to be read")
                .creationDate(Timestamp.from(Instant.now()))
                .notificationOwner(user)
                .build();
        notification = notificationRepository.save(notification);

        notification.setRead(true);
        notification = notificationRepository.save(notification);

        Optional<Notification> updatedNotification = notificationRepository.findById(notification.getId());
        assertTrue(updatedNotification.isPresent());
        assertTrue(updatedNotification.get().isRead());
    }
}