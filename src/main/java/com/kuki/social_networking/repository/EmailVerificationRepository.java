package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.EmailVerification;
import com.kuki.social_networking.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for managing {@link EmailVerification} entities.
 */
@Repository
public interface EmailVerificationRepository extends CrudRepository<EmailVerification, UUID> {

    /**
     * Finds an email verification entity by the user it belongs to.
     *
     * @param registeredUser the user to find the email verification entity for
     * @return the email verification entity for the user
     */
    Optional<EmailVerification> findByUser(User registeredUser);

    /**
     * Finds an email verification entity by the username of the user it belongs to.
     *
     * @param username the username of the user to find the email verification entity for
     * @return the email verification entity for the user
     */
    Optional<EmailVerification> findByUser_Username(String username);

    /**
     * Finds an email verification entity by the token.
     *
     * @param uuid the token to find the email verification entity for
     * @return the email verification entity for the token
     */
    Optional<EmailVerification> findByToken(UUID uuid);
}
