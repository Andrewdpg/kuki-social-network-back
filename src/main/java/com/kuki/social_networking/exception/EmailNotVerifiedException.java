package com.kuki.social_networking.exception;

public class EmailNotVerifiedException extends RuntimeException {
    public EmailNotVerifiedException(String message) {
        super(message);
    }
}
