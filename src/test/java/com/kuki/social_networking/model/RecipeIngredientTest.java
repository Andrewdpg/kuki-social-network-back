package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@ExtendWith(MockitoExtension.class)
public class RecipeIngredientTest {

    private Validator validator;

    @Mock
    private Recipe mockRecipe;

    @Mock
    private Ingredient mockIngredient;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRecipeIngredientCreation() {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .id(UUID.randomUUID())
                .recipe(mockRecipe)
                .ingredient(mockIngredient)
                .quantity(100.0f)
                .build();

        assertNotNull(recipeIngredient);
        assertNotNull(recipeIngredient.getId());
        assertEquals(mockRecipe, recipeIngredient.getRecipe());
        assertEquals(mockIngredient, recipeIngredient.getIngredient());
        assertEquals(100.0f, recipeIngredient.getQuantity());
    }

    // TODO: Fix test
    @Test
    @Disabled("This test is not working")
    public void testRecipeIngredientValidation() {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        Set<ConstraintViolation<RecipeIngredient>> violations = validator.validate(recipeIngredient);
        assertFalse(violations.isEmpty());

        recipeIngredient.setRecipe(mockRecipe);
        recipeIngredient.setIngredient(mockIngredient);
        recipeIngredient.setQuantity(100.0f);
        violations = validator.validate(recipeIngredient);
        assertTrue(violations.isEmpty());

    }

    @Test
    public void testRecipeIngredientQuantityNotNegative() {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(mockRecipe)
                .ingredient(mockIngredient)
                .quantity(-1.0f)
                .build();

        Set<ConstraintViolation<RecipeIngredient>> violations = validator.validate(recipeIngredient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must be greater than or equal to 0")));
    }

    // TODO: Fix test
    @Test
    @Disabled("This test is not working")
    public void testRecipeIngredientRecipeNotNull() {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .ingredient(mockIngredient)
                .quantity(100.0f)
                .build();

        Set<ConstraintViolation<RecipeIngredient>> violations = validator.validate(recipeIngredient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must not be null")));
    }

    // TODO: Fix test
    @Test
    @Disabled("This test is not working")
    public void testRecipeIngredientIngredientNotNull() {
        RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .recipe(mockRecipe)
                .quantity(100.0f)
                .build();

        Set<ConstraintViolation<RecipeIngredient>> violations = validator.validate(recipeIngredient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("must not be null")));
    }
}