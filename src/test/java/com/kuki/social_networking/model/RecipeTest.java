package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
public class RecipeTest {

    private Validator validator;

    @Mock
    private User mockUser;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testRecipeCreation() {
        Recipe recipe = Recipe.builder()
                .id(UUID.randomUUID())
                .title("Spaghetti Carbonara")
                .description("Classic Italian pasta dish")
                .photoUrl("http://example.com/carbonara.jpg")
                .publishDate(Timestamp.from(Instant.now()))
                .difficulty(RecipeDifficulty.INTERMEDIATE)
                .country("Italy")
                .estimatedTime(Duration.ofMinutes(30))
                .recipeOwner(mockUser)
                .steps(new ArrayList<>())
                .comments(new ArrayList<>())
                .mealDays(new ArrayList<>())
                .ingredients(new ArrayList<>())
                .tags(new ArrayList<>())
                .likes(new ArrayList<>())
                .usersWhoSaved(new ArrayList<>())
                .build();

        assertNotNull(recipe);
        assertNotNull(recipe.getId());
        assertEquals("Spaghetti Carbonara", recipe.getTitle());
        assertEquals("Classic Italian pasta dish", recipe.getDescription());
        assertEquals("http://example.com/carbonara.jpg", recipe.getPhotoUrl());
        assertNotNull(recipe.getPublishDate());
        assertEquals(RecipeDifficulty.INTERMEDIATE, recipe.getDifficulty());
        assertEquals("Italy", recipe.getCountry());
        assertEquals(Duration.ofMinutes(30), recipe.getEstimatedTime());
        assertEquals(mockUser, recipe.getRecipeOwner());
        assertTrue(recipe.getSteps().isEmpty());
        assertTrue(recipe.getComments().isEmpty());
        assertTrue(recipe.getMealDays().isEmpty());
        assertTrue(recipe.getIngredients().isEmpty());
        assertTrue(recipe.getTags().isEmpty());
        assertTrue(recipe.getLikes().isEmpty());
        assertTrue(recipe.getUsersWhoSaved().isEmpty());
    }

    @Test
    public void testRecipeValidation() {
        Recipe recipe = new Recipe();
        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);
        assertFalse(violations.isEmpty());

        recipe.setTitle("Spaghetti Carbonara");
        recipe.setDescription("Classic Italian pasta dish");
        recipe.setPhotoUrl("http://example.com/carbonara.jpg");
        recipe.setPublishDate(Timestamp.from(Instant.now()));
        recipe.setDifficulty(RecipeDifficulty.INTERMEDIATE);
        recipe.setCountry("Italy");
        recipe.setEstimatedTime(Duration.ofMinutes(30));
        recipe.setRecipeOwner(mockUser);
        violations = validator.validate(recipe);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testRecipeTitleNotBlank() {
        Recipe recipe = Recipe.builder()
                .title("")
                .description("Classic Italian pasta dish")
                .photoUrl("http://example.com/carbonara.jpg")
                .country("Italy")
                .build();

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Recipe title is mandatory")));
    }

    @Test
    public void testRecipeDescriptionNotBlank() {
        Recipe recipe = Recipe.builder()
                .title("Spaghetti Carbonara")
                .description("")
                .photoUrl("http://example.com/carbonara.jpg")
                .country("Italy")
                .build();

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Recipe description is mandatory")));
    }

    @Test
    public void testRecipePhotoUrlNotBlank() {
        Recipe recipe = Recipe.builder()
                .title("Spaghetti Carbonara")
                .description("Classic Italian pasta dish")
                .photoUrl("")
                .country("Italy")
                .build();

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Recipe photo is mandatory")));
    }

    @Test
    public void testRecipeCountryNotBlank() {
        Recipe recipe = Recipe.builder()
                .title("Spaghetti Carbonara")
                .description("Classic Italian pasta dish")
                .photoUrl("http://example.com/carbonara.jpg")
                .country("")
                .build();

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("Recipe country is mandatory")));
    }
}

