package com.kuki.social_networking.exception;

/**
 * Exception thrown when a requested permission is not found.
 * This exception extends {@link RuntimeException}.
 * 
 * @param message The detail message explaining the reason for the exception.
 */
public class PermissionNotFoundException extends RuntimeException {
    public PermissionNotFoundException(String message) {
        super(message);
    }
}