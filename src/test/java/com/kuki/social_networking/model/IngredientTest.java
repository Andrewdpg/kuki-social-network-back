package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
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
public class IngredientTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Test the creation of an Ingredient object and verifies that all fields are correctly set.
     */
    @Test
    public void testIngredientCreation() {
        Map<String, Object> nutritionalInfo = new HashMap<>();
        nutritionalInfo.put("calories", 50);
        nutritionalInfo.put("protein", 2);

        Ingredient ingredient = Ingredient.builder()
                .id(UUID.randomUUID())
                .name("Apple")
                .photoUrl("http://example.com/apple.jpg")
                .nutritionalInfo(nutritionalInfo)
                .unit(IngredientUnit.PIECES)
                .build();

        assertNotNull(ingredient);
        assertNotNull(ingredient.getId());
        assertEquals("Apple", ingredient.getName());
        assertEquals("http://example.com/apple.jpg", ingredient.getPhotoUrl());
        assertEquals(nutritionalInfo, ingredient.getNutritionalInfo());
        assertEquals(IngredientUnit.PIECES, ingredient.getUnit());
    }

    /**
     * Validates an Ingredient object with missing mandatory fields, expecting validation errors.
     * Then, corrects the fields and revalidates, expecting no validation errors.
     */
    @Test
    public void testIngredientValidation() {
        Ingredient ingredient = new Ingredient();
        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        assertEquals(2, violations.size());

        ingredient.setName("Apple");
        ingredient.setPhotoUrl("http://example.com/apple.jpg");
        violations = validator.validate(ingredient);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that an Ingredient object's name cannot be blank and that the appropriate validation
     * error message is generated.
     */
    @Test
    public void testIngredientNameNotBlank() {
        Ingredient ingredient = Ingredient.builder()
                .name("")
                .photoUrl("http://example.com/apple.jpg")
                .build();

        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Ingredient name is mandatory")));
    }

    /**
     * Tests that an Ingredient object's photo URL cannot be blank and that the appropriate validation
     * error message is generated.
     */
    @Test
    public void testIngredientPhotoNotBlank() {
        Ingredient ingredient = Ingredient.builder()
                .name("Apple")
                .photoUrl("")
                .build();

        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Ingredient photo is mandatory")));
    }

    /**
     * Tests that the default value for an Ingredient's nutritional information map is an empty map.
     */
    @Test
    public void testNutritionalInfoDefaultValue() {
        Ingredient ingredient = new Ingredient();
        assertNotNull(ingredient.getNutritionalInfo());
        assertTrue(ingredient.getNutritionalInfo().isEmpty());
    }

    /**
     * Tests that the unit of measurement for an Ingredient is correctly set.
     */
    @Test
    public void testIngredientUnit() {
        Ingredient ingredient = Ingredient.builder()
                .name("Flour")
                .photoUrl("http://example.com/flour.jpg")
                .unit(IngredientUnit.GRAMS)
                .build();

        assertEquals(IngredientUnit.GRAMS, ingredient.getUnit());
    }
}