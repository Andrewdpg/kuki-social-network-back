package com.kuki.social_networking.controller.rest;


import com.kuki.social_networking.mapper.NotificationMapper;
import com.kuki.social_networking.model.Notification;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.response.NotificationResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.NotificationService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;
import org.springframework.data.domain.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    /**
     * Retrieves notifications for the authenticated user.
     *
     * @param user the authenticated user details
     * @param page the page number
     * @param unread whether to retrieve only unread notifications
     * @return a ResponseEntity containing a page of NotificationResponse objects
     */
    @GetMapping("")
    public ResponseEntity<Page<NotificationResponse>> getNotifications(
        @AuthenticatedUser CustomUserDetails user,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "unread", defaultValue = "false") boolean unread
    ) {

        PageableRequest request = new PageableRequest();
        request.setPage(page);
        request.setSortBy("creationDate");

        if (unread)
            return getUnreadNotifications(user, request);

        Page<Notification> notifications = notificationService
            .getNotificationsByUser(user, request);

        return ResponseEntity.ok(notifications.map(notificationMapper::toNotificationResponse));
    }

    /**
     * Retrieves unread notifications for the authenticated user.
     *
     * @param user the authenticated user details
     * @param request the pageable request
     * @return a ResponseEntity containing a page of NotificationResponse objects
     */
    private ResponseEntity<Page<NotificationResponse>> getUnreadNotifications(
        CustomUserDetails user,
        PageableRequest request
    ) {
        Page<Notification> notifications = notificationService.getUnreadNotifications(user, request);
        return ResponseEntity.ok(notifications.map(notificationMapper::toNotificationResponse));
    }

    /**
     * Marks a notification as read.
     *
     * @return a ResponseEntity containing the updated Notification object
     */
    @PutMapping("/read/{notificationId}")
    public ResponseEntity<NotificationResponse> markNotificationAsRead(
        @AuthenticatedUser CustomUserDetails user,
        @PathVariable UUID notificationId) {

        Notification notification = notificationService.markNotificationAsRead(user, notificationId);
        return ResponseEntity.ok(notificationMapper.toNotificationResponse(notification));
    }

}
