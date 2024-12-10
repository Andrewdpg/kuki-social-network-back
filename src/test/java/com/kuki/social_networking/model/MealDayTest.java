package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class MealDayTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test the creation of a MealDay object and verifies that all fields are correctly set.
     */
    @Test
    public void testMealDayCreation() {
        MealPlanner planner = new MealPlanner();
        MealDay mealDay = MealDay.builder()
                .id(UUID.randomUUID())
                .day(1)
                .description("Monday meals")
                .mealPlanner(planner)
                .recipes(new ArrayList<>())
                .build();

        assertNotNull(mealDay);
        assertNotNull(mealDay.getId());
        assertEquals(1, mealDay.getDay());
        assertEquals("Monday meals", mealDay.getDescription());
        assertEquals(planner, mealDay.getMealPlanner());
        assertTrue(mealDay.getRecipes().isEmpty());
    }

    /**
     * Validates a MealDay object with missing mandatory fields, expecting validation errors.
     * Then, corrects the fields and revalidates, expecting no validation errors.
     */
    @Test
    public void testMealDayValidation() {
        MealDay mealDay = new MealDay();
        Set<ConstraintViolation<MealDay>> violations = validator.validate(mealDay);
        assertEquals(2, violations.size());

        mealDay.setDay(1);
        mealDay.setDescription("Monday meals");
        mealDay.setMealPlanner(new MealPlanner());
        violations = validator.validate(mealDay);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a MealDay object's description cannot be blank and that the appropriate validation
     * error message is generated.
     */
    @Test
    public void testMealDayDescriptionNotBlank() {
        MealDay mealDay = MealDay.builder()
                .day(1)
                .description("")
                .mealPlanner(new MealPlanner())
                .build();

        Set<ConstraintViolation<MealDay>> violations = validator.validate(mealDay);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Meal day description is mandatory")));
    }

    /**
     * Tests that a MealDay object's day value cannot be below the minimum value (1).
     */
    @Test
    public void testMealDayMinDay() {
        MealDay mealDay = MealDay.builder()
                .day(0)
                .description("Invalid day")
                .mealPlanner(new MealPlanner())
                .build();

        Set<ConstraintViolation<MealDay>> violations = validator.validate(mealDay);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a MealDay object's day value cannot exceed the maximum value (30 or 31).
     */
    @Test
    public void testMealDayMaxDay() {
        MealDay mealDay = MealDay.builder()
                .day(31)
                .description("Invalid day")
                .mealPlanner(new MealPlanner())
                .build();

        Set<ConstraintViolation<MealDay>> violations = validator.validate(mealDay);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests the functionality of adding recipes to a MealDay and ensures
     * that the list of recipes is correctly managed.
     */
    @Test
    public void testMealDayRecipes() {
        MealDay mealDay = new MealDay();
        Recipe recipe1 = new Recipe();
        Recipe recipe2 = new Recipe();

        mealDay.setRecipes(List.of(recipe1, recipe2));

        assertEquals(2, mealDay.getRecipes().size());
        assertTrue(mealDay.getRecipes().contains(recipe1));
        assertTrue(mealDay.getRecipes().contains(recipe2));
    }
}

