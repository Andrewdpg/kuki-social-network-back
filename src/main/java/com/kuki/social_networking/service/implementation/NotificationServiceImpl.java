package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.model.Notification;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.repository.NotificationRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.exception.NotificationNotFoundException;
import com.kuki.social_networking.exception.UserNotFoundException;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

/**
 * Implementation of the notification service.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Sends a notification to a user.
     *
     * @param username The username of the user to be notified.
     * @param content The content of the notification.
     * @param url The URL related to the notification.
     */
    @Override
    public void sendNotification(String username, String content, String url) {
        User user = searchUserByUsername(username);
        System.out.println("Sending notification to user: " + user.getUsername());
        Notification notification = Notification.builder()
            .id(UUID.randomUUID())
            .content(content)
            .url(url)
            .creationDate(Timestamp.from(Instant.now()))
            .isRead(false)
            .notificationOwner(user)
            .build();

        //TODO: implement real-time in-app notifications via WebSocket (my be a little complex, so, let's leave it for later)
        notificationRepository.save(notification);
    }

    /**
     * Retrieves unread notifications for a specific user.
     *
     * @param userDetails The user who is requesting the notifications.
     * @return A list of unread notifications.
     */
    @Override
    public Page<Notification> getUnreadNotifications(CustomUserDetails userDetails, PageableRequest request) {
        User user = searchUserByUsername(userDetails.getUsername());
        return notificationRepository.findByNotificationOwnerAndIsReadIsFalse(user, request.pageable());
    }

    /**
     * Marks a notification as read.
     *
     * @param userDetails The user who is marking the notification as read.
     * @param notificationId The ID of the notification to be marked as read.
     */
    @Override
    public Notification markNotificationAsRead(CustomUserDetails userDetails, UUID notificationId) {
        User user = searchUserByUsername(userDetails.getUsername());

        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new NotificationNotFoundException("Notification not found with ID: " + notificationId));

        if (!notification.getNotificationOwner().equals(user)) {
            throw new IllegalArgumentException("The user is not the owner of the notification");
        }

        notification.setRead(true);
        notificationRepository.save(notification);

        return notification;
    }

    /**
     * Retrieves all notifications for a specific user, ordered by creation date.
     *
     * @param userDetails The user who is requesting the notifications.
     * @param request The pagination and sorting information.
     * @return A list of notifications ordered by creation date.
     */
    @Override
    public Page<Notification> getNotificationsByUser(CustomUserDetails userDetails, PageableRequest request) {
        if(userDetails == null) {
            throw new IllegalArgumentException("User is not authenticated");
        }

        User user = searchUserByUsername(userDetails.getUsername());

        return notificationRepository.findByNotificationOwnerOrderByCreationDateDesc(user, request.pageable());
    }

    private User searchUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
    }
}

