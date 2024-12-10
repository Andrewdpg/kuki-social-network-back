package com.kuki.social_networking.exception;

/**
 * Exception thrown when a specified country is not found.
 * This exception extends {@link RuntimeException}.
 * 
 * @param message the detail message explaining the reason for the exception
 */
public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(String message) {
        super(message);
    }

}
