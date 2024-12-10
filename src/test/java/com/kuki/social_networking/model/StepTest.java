package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class StepTest {

    private Validator validator;

    @Mock
    private Recipe mockRecipe;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test the creation of a Step object and verifies that all fields are correctly set.
     */
    @Test
    public void testStepCreation() {
        Step step = Step.builder()
                .id(UUID.randomUUID())
                .number(1)
                .description("Mix the ingredients")
                .multimediaUrl("http://example.com/step1.jpg")
                .recipe(mockRecipe)
                .build();

        assertNotNull(step);
        assertNotNull(step.getId());
        assertEquals(1, step.getNumber());
        assertEquals("Mix the ingredients", step.getDescription());
        assertEquals("http://example.com/step1.jpg", step.getMultimediaUrl());
        assertEquals(mockRecipe, step.getRecipe());
    }

    /**
     * Validates a Step object with missing mandatory fields, expecting validation errors.
     * Then, corrects the fields and revalidates, expecting no validation errors.
     */
    @Test
    public void testStepValidation() {
        Step step = new Step();
        Set<ConstraintViolation<Step>> violations = validator.validate(step);
        assertFalse(violations.isEmpty());

        step.setNumber(1);
        step.setDescription("Mix the ingredients");
        step.setRecipe(mockRecipe);
        violations = validator.validate(step);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a Step object's number cannot be below the minimum value (0).
     */
    @Test
    public void testStepNumberMinValue() {
        Step step = Step.builder()
                .number(-1)
                .description("Mix the ingredients")
                .recipe(new Recipe())
                .build();

        Set<ConstraintViolation<Step>> violations = validator.validate(step);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a Step object's description cannot be blank and that the appropriate validation
     * error message is generated.
     */
    @Test
    public void testStepDescriptionNotBlank() {
        Step step = Step.builder()
                .number(1)
                .description("")
                .recipe(new Recipe())
                .build();

        Set<ConstraintViolation<Step>> violations = validator.validate(step);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Step description is mandatory")));
    }
}

