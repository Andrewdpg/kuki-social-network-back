package com.kuki.social_networking.service.implementation;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import com.kuki.social_networking.data.MailBody;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kuki.social_networking.exception.InvalidResourceException;
import com.kuki.social_networking.model.EmailVerification;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.repository.EmailVerificationRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.service.interfaces.EmailService;

import lombok.RequiredArgsConstructor;

import static com.kuki.social_networking.config.Path.VERIFICATION_PATH;

/**
 * Implementation of the email service.
 */
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;


    private final JavaMailSender mailSender;
    private final Environment env;

    /**
     * Sends an email in a separate thread to avoid blocking the main application flow.
     *
     * @param mail the {@link MailBody} object containing the email details such as subject, text, and recipient.
     */
    public void sendEmail(MailBody mail) {
        new Thread(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(env.getProperty("MAIL_USERNAME"));
            message.setSubject(mail.subject());
            message.setText(mail.text());
            message.setTo(mail.to());

            mailSender.send(message);
        }).start();
    }

    /**
     * Sends a verification email to the user.
     *
     * @param user The user to send the email to.
     * @return The email verification object.
     */
    @Override
    public EmailVerification sendVerificationEmail(User user) {
        emailVerificationRepository.findByUser(user).ifPresent(emailVerificationRepository::delete);

        Timestamp expiration = Timestamp.from(Instant.now().plusSeconds(60*60*24*3));

        EmailVerification verification = emailVerificationRepository.save(EmailVerification.builder().user(user).expiration(expiration).build());

        sendEmail(MailBody.builder()
            .to(user.getEmail())
            .subject("Email Verification")
            .text("Welcome to Kuki! \n\n" +
                "Thank you for signing up. Please verify your email to complete the registration process. \n" +
                "If you did not sign up for an account, please ignore this email. \n\n" +
                "Click the link to verify your email: "+ VERIFICATION_PATH +"?token=" + verification.getToken())
            .build());

        return verification;
    }

    /**
     * Verifies the user's email.
     *
     * @param token The verification token.
     */
    @Override
    public void verifyEmail(String token) {
        EmailVerification verification = emailVerificationRepository.findByToken(UUID.fromString(token))
            .orElseThrow(() -> new InvalidResourceException("Invalid token"));

        if (verification.getExpiration().before(Timestamp.from(Instant.now()))) {
            emailVerificationRepository.delete(verification);
            throw new InvalidResourceException("Token has expired");
        }

        User user = verification.getUser();
        user.setEmailVerified(true);
        userRepository.save(user);
        emailVerificationRepository.delete(verification);
    }
}
