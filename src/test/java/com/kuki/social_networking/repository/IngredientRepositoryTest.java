package com.kuki.social_networking.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kuki.social_networking.model.Ingredient;
import com.kuki.social_networking.model.IngredientUnit;

@SpringBootTest
@Transactional
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    private Ingredient ingredient;

    @BeforeEach
    public void setUp() {
        ingredient = Ingredient.builder()
                .name("Tomato")
                .photoUrl("http://example.com/tomato.jpg")
                .unit(IngredientUnit.PIECES)
                .build();
        ingredientRepository.save(ingredient);
    }

    /**
     * Tests the saving and retrieval of an ingredient.
     * This test ensures that:
     * 1. An ingredient can be successfully saved to the database.
     * 2. The saved ingredient can be retrieved using its ID.
     * 3. The retrieved ingredient has the correct name, photo URL, and unit.
     */
    @Test
    public void testSaveAndRetrieveIngredient() {
        Ingredient found = ingredientRepository.findById(ingredient.getId()).orElse(null);
        assertNotNull(found);
        assertEquals("Tomato", found.getName());
        assertEquals("http://example.com/tomato.jpg", found.getPhotoUrl());
        assertEquals(IngredientUnit.PIECES, found.getUnit());
    }

    /**
     * Tests the findByNameContainingIgnoreCase method of the IngredientRepository.
     * This test verifies that:
     * 1. Ingredients can be found by a partial, case-insensitive name match.
     * 2. The returned list is not empty when searching for an existing ingredient.
     * 3. The returned list contains an ingredient with a name that matches the search term (case-insensitive).
     */
    @Test
    public void testFindByNameContainingIgnoreCase() {
        List<Ingredient> ingredients = ingredientRepository.findByNameContainingIgnoreCase("tomato");
        assertFalse(ingredients.isEmpty());
        assertTrue(ingredients.stream().anyMatch(i -> i.getName().equalsIgnoreCase("Tomato")));
    }

    /**
     * Tests the deletion of an ingredient from the database.
     * This test ensures that:
     * 1. An ingredient can be successfully deleted using its ID.
     * 2. After deletion, the ingredient no longer exists in the database.
     */
    @Test
    public void testDeleteIngredient() {
        ingredientRepository.deleteById(ingredient.getId());
        assertFalse(ingredientRepository.findById(ingredient.getId()).isPresent());
    }
}