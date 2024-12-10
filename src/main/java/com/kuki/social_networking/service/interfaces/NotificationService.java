package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.Notification;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Interface for managing notifications in the application.
 */
public interface NotificationService {

    /**
     * Sends a notification to a user.
     *
     * @param username The username of the user to be notified.
     * @param content The content of the notification.
     * @param url The URL related to the notification (e.g., link to the comment or recipe).
     */
    void sendNotification(String username, String content, String url);

    /**
     * Retrieves unread notifications for a specific user.
     *
     * @param userDetails The user for whom the notifications are to be retrieved.
     * @return A list of unread notifications.
     */
    Page<Notification> getUnreadNotifications(CustomUserDetails userDetails, PageableRequest request);

    /**
     * Marks a notification as read.
     *
     * @param userDetails The user who is marking the notification as read.
     * @param notificationId The ID of the notification to be marked as read.
     * @return The updated notification.
     */
    Notification markNotificationAsRead(CustomUserDetails userDetails, UUID notificationId);

    /**
     * Retrieves all notifications for a specific user, ordered by creation date.
     *
     * @param userDetails The user who is requesting the notifications.
     * @return A list of notifications ordered by creation date.
     */
    Page<Notification> getNotificationsByUser(CustomUserDetails userDetails, PageableRequest request);
}

