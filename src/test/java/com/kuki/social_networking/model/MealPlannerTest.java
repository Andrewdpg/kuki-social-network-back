package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class MealPlannerTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test the creation of a MealPlanner object and verifies that all fields are correctly set.
     */
    @Test
    public void testMealPlannerCreation() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .id(UUID.randomUUID())
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .isPublic(true)
                .mealDays(new ArrayList<>())
                .userMealPlanners(new ArrayList<>())
                .build();

        assertNotNull(mealPlanner);
        assertNotNull(mealPlanner.getId());
        assertEquals("Weekly Plan", mealPlanner.getName());
        assertEquals("A balanced meal plan for the week", mealPlanner.getDescription());
        assertTrue(mealPlanner.getIsPublic());
        assertTrue(mealPlanner.getMealDays().isEmpty());
        assertTrue(mealPlanner.getUserMealPlanners().isEmpty());
    }

    /**
     * Validates a MealPlanner object with missing mandatory fields, expecting validation errors.
     * Then, corrects the fields and revalidates, expecting no validation errors.
     */
    @Test
    public void testMealPlannerValidation() {
        MealPlanner mealPlanner = new MealPlanner();
        Set<ConstraintViolation<MealPlanner>> violations = validator.validate(mealPlanner);
        assertEquals(2, violations.size());

        mealPlanner.setName("Weekly Plan");
        mealPlanner.setDescription("A balanced meal plan for the week");
        violations = validator.validate(mealPlanner);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a MealPlanner object's name cannot be blank and that the appropriate validation
     * error message is generated.
     */
    @Test
    public void testMealPlannerNameNotBlank() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("")
                .description("A balanced meal plan for the week")
                .build();

        Set<ConstraintViolation<MealPlanner>> violations = validator.validate(mealPlanner);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Meal planner name is mandatory")));
    }

    /**
     * Tests that a MealPlanner object's description cannot be blank and that the appropriate validation
     * error message is generated.
     */
    @Test
    public void testMealPlannerDescriptionNotBlank() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("")
                .build();

        Set<ConstraintViolation<MealPlanner>> violations = validator.validate(mealPlanner);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Meal planner description is mandatory")));
    }

    /**
     * Tests that the default value for a MealPlanner's isPublic field is false.
     */
    @Test
    public void testMealPlannerIsPublicDefaultValue() {
        MealPlanner mealPlanner = new MealPlanner();
        assertFalse(mealPlanner.getIsPublic());
    }
}

