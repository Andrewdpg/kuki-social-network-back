package com.kuki.social_networking.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String userNotAuthenticated) {
        super(userNotAuthenticated);
    }
}
