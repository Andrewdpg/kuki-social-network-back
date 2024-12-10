package com.kuki.social_networking.exception;

/**
 * Exception thrown when a meal planner is not found.
 */
public class MealPlannerNotFoundException extends RuntimeException {
    public MealPlannerNotFoundException(String message) {
        super(message);
    }
}