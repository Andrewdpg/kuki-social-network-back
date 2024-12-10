package com.kuki.social_networking.exception;

/**
 * Exception thrown when a specified role is not found in the system.
 * This exception extends {@link RuntimeException}, indicating that it is an unchecked exception.
 * 
 * @param message the detail message explaining the reason for the exception
 */
public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}