package com.kuki.social_networking.exception;

public class MealDayNotFoundException extends RuntimeException {
    public MealDayNotFoundException(String mealPlannerNotFound) {
        super(mealPlannerNotFound);
    }
}
