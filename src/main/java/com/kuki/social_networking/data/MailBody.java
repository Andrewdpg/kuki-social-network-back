package com.kuki.social_networking.data;

import lombok.Builder;

@Builder
public record MailBody(
    String to,
    String subject,
    String text) {
}
