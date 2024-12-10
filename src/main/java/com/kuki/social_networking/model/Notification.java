package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.UUID;

import lombok.*;

/**
 * This class represents the notifications sent to the users.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification", indexes = {
    @Index(name = "notification_notification_owner_index", columnList = "notification_owner")
})
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Notification content is mandatory")
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Builder.Default
    @Getter
    private boolean isRead = false;

    @Column(nullable = false)
    private Timestamp creationDate;

    @Column
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "notification_owner", nullable = false)
    private User notificationOwner;

    public boolean getIsRead() {
        return isRead;
    }
}
