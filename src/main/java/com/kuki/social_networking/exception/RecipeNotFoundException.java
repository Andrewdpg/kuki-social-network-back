package com.kuki.social_networking.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(String message) {
        super(message);
    }
}
