package com.kuki.social_networking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * Model class for email verification.
 */
@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "email_verification", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_username"})
})
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID token;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private Timestamp expiration;
}
