package com.kuki.social_networking.exception;

/**
 * Exception thrown when a specified comment is not found.
 * This exception extends {@link RuntimeException}.
 */
public class CommentNotFoundException extends RuntimeException{

    /**
     * Constructor for the CommentNotFoundException.
     * @param message the detail message explaining the reason for the exception
     */
    public CommentNotFoundException(String message) {
        super(message);
    }
}
