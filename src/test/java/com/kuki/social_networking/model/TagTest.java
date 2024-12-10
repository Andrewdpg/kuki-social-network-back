package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
public class TagTest {
    
    @Mock
    private List<Recipe> mockRecipes;

    /**
     * Test the creation of a Tag object and verifies that all fields are correctly set.
     */
    @Test
    public void testTagCreation() {
        Tag tag = Tag.builder()
                .name("Vegetarian")
                .recipes(new ArrayList<>())
                .build();

        assertNotNull(tag);
        assertEquals("Vegetarian", tag.getName());
        assertTrue(tag.getRecipes().isEmpty());
    }

    /**
     * Tests the equality of two Tag objects that have the same name, expecting them to be equal.
     * Also tests that two Tag objects with different names are not equal.
     */
    @Test
    public void testTagEquality() {
        Tag tag1 = new Tag("Vegetarian", mockRecipes);
        Tag tag2 = new Tag("Vegetarian", mockRecipes);
        Tag tag3 = new Tag("Vegan", mockRecipes);

        assertEquals(tag1, tag2);
        assertNotEquals(tag1, tag3);
    }

    /**
     * Tests that two Tag objects with the same name have the same hash code.
     */
    @Test
    public void testTagHashCode() {
        Tag tag1 = new Tag("Vegetarian", mockRecipes);
        Tag tag2 = new Tag("Vegetarian", mockRecipes);

        assertEquals(tag1.hashCode(), tag2.hashCode());
    }
}
