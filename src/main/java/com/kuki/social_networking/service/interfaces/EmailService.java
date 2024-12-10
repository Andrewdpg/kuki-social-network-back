package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.data.MailBody;
import com.kuki.social_networking.model.EmailVerification;
import com.kuki.social_networking.model.User;

public interface EmailService {

    /**
     * Sends an email in a separate thread to avoid blocking the main application flow.
     *
     * @param mail the {@link MailBody} object containing the email details such as subject, text, and recipient.
     */
    void sendEmail(MailBody mail);

    /**
     * Send a verification email to the user.
     *
     * @param user The user to send the email to.
     */
    EmailVerification sendVerificationEmail(User user);

    /**
     * Verify the email of the user.
     *
     * @param token The token to verify the email.
     */
    void verifyEmail(String token);

}
