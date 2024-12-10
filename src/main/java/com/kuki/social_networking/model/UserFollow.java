package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents the follow relation between two users.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_follow", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
})
public class UserFollow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Timestamp creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne(optional = false)
    @JoinColumn(name = "followed_id", nullable = false)
    private User followed;
}
