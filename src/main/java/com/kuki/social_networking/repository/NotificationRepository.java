package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.Notification;
import java.util.UUID;

import com.kuki.social_networking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository for managing {@link Notification} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for complex queries.
 */
public interface NotificationRepository extends JpaRepository<Notification, UUID>, JpaSpecificationExecutor<Notification> {

    /**
     * Finds unread notifications for a specific user by their username.
     *
     * @param user the user.
     * @return a list of unread notifications for the specified user.
     */
    Page<Notification> findByNotificationOwnerAndIsReadIsFalse(User user, Pageable pageable);


    /**
     * Finds all notifications for a specific user, ordered by creation date.
     *
     * @param user the user.
     * @return a list of notifications ordered by their creation date.
     */
    Page<Notification> findByNotificationOwnerOrderByCreationDateDesc(User user, Pageable pageable);

}

