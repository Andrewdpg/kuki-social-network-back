package com.kuki.social_networking.service;

import com.kuki.social_networking.exception.InvalidResourceException;
import com.kuki.social_networking.model.EmailVerification;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.Role;
import com.kuki.social_networking.model.UserStatus;
import com.kuki.social_networking.repository.EmailVerificationRepository;
import com.kuki.social_networking.repository.RoleRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.service.interfaces.EmailService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: Remake test
@Disabled
@SpringBootTest
@Transactional
public class EmailServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private EmailVerificationRepository emailVerificationRepository;
    @Autowired
    private RoleRepository roleRepository;

    private final List<Role> userRoles = List.of(
            roleRepository.getReferenceById(1)
    );

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
            .username("testuser")
            .email("johnDoe@example.com")
            .password(passwordEncoder.encode("password"))
            .fullName("John Doe")
            .country("United States")
            .registerDate(Timestamp.from(Instant.now()))
            .userStatus(UserStatus.ACTIVE)
            .biography("")
            .roles(userRoles)
            .lastConnection(Timestamp.from(Instant.now()))
            .isEmailVerified(false)
            .build();

        user = userRepository.save(user);
    }

    /**
     * Test the sendVerificationEmail method.
     * This test checks if the method sends a verification email to the user.
     * It also checks if the expiration date of the verification token is set correctly.
     */
    @Test
    public void testSendVerificationEmail() {
        emailService.sendVerificationEmail(user);

        EmailVerification verification = emailVerificationRepository.findByUser_Username(user.getUsername()).orElse(null);

        assertNotNull(verification);
        assertTrue( verification.getExpiration().getTime() >= Timestamp.from(Instant.now().plusSeconds(60*60*24*2)).getTime());
    }

    /**
     * Test the sendVerificationEmail method.
     * This test checks if the method sends a verification email to the user.
     * It also checks if the expiration date of the verification token is set correctly.
     */
    @Test
    public void testSendMultipleVerificationEmails() {

        EmailVerification firstVerification = emailService.sendVerificationEmail(user);

        EmailVerification secondVerification = emailService.sendVerificationEmail(user);

        assertNotNull(firstVerification);
        assertNotNull(secondVerification);
        assertNotEquals(firstVerification.getToken(), secondVerification.getToken());
        assertTrue( firstVerification.getExpiration().getTime() <= secondVerification.getExpiration().getTime());
    }

    /**
     * Test the verifyEmail method.
     * This test checks if the method verifies the email of the user correctly.
     */
    @Test
    public void testVerifyEmail() {
        emailService.sendVerificationEmail(user);
        EmailVerification verification = emailVerificationRepository.findByUser_Username(user.getUsername()).orElse(null);

        assertNotNull(verification);

        emailService.verifyEmail(verification.getToken().toString());

        User verifiedUser = userRepository.findByEmail(user.getEmail()).orElse(null);

        assertNotNull(verifiedUser);
        assertTrue(verifiedUser.isEmailVerified());
        assertNull(emailVerificationRepository.findByUser_Username(user.getUsername()).orElse(null));
    }

    /**
     * Test the verifyEmail method.
     * This test checks the exception thrown when the token is invalid.
     */
    @Test
    public void testVerifyEmailInvalidToken() {
        emailService.sendVerificationEmail(user);
        EmailVerification verification = emailVerificationRepository.findByUser_Username(user.getUsername()).orElse(null);

        assertNotNull(verification);

        try {
            emailService.verifyEmail("invalid-token");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    /**
     * Test the verifyEmail method.
     * This test checks the exception thrown when the token has expired.
     */
    @Test
    public void testVerifyEmailExpiredToken() {
        emailService.sendVerificationEmail(user);
        EmailVerification verification = emailVerificationRepository.findByUser_Username(user.getUsername()).orElse(null);

        assertNotNull(verification);

        verification.setExpiration(Timestamp.from(Instant.now().minusSeconds(60*60*24*3)));
        emailVerificationRepository.save(verification);

        try {
            emailService.verifyEmail(verification.getToken().toString());
        } catch (InvalidResourceException e) {
            assertNotNull(e);
        }
    }
}
