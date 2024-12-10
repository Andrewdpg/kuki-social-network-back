package com.kuki.social_networking.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserMealPlannerTest {

    @Mock
    private User mockUser;
    
    @Mock
    private MealPlanner mockMealPlanner;
    
    /**
     * Test the creation of a UserMealPlanner object and verifies that all fields are correctly set.
     */
    @Test
    public void testUserMealPlannerCreation() {
        UserMealPlanner userMealPlanner = UserMealPlanner.builder()
                .id(UUID.randomUUID())
                .user(mockUser)
                .mealPlanner(mockMealPlanner)
                .isOwner(true)
                .isSaved(true)
                .build();

        assertNotNull(userMealPlanner);
        assertNotNull(userMealPlanner.getId());
        assertEquals(mockUser, userMealPlanner.getUser());
        assertEquals(mockMealPlanner, userMealPlanner.getMealPlanner());
        assertTrue(userMealPlanner.isOwner());
        assertTrue(userMealPlanner.isSaved());
    }

    /**
     * Tests the isOwner flag for the UserMealPlanner object and verifies its correct functionality.
     */
    @Test
    public void testUserMealPlannerIsOwner() {
        UserMealPlanner userMealPlanner = UserMealPlanner.builder()
                .user(mockUser)
                .mealPlanner(mockMealPlanner)
                .isOwner(false)
                .isSaved(true)
                .build();

        assertFalse(userMealPlanner.isOwner());
    }

    /**
     * Tests the isSaved flag for the UserMealPlanner object and verifies its correct functionality.
     */
    @Test
    public void testUserMealPlannerIsSaved() {
        UserMealPlanner userMealPlanner = UserMealPlanner.builder()
                .user(mockUser)
                .mealPlanner(mockMealPlanner)
                .isOwner(true)
                .isSaved(false)
                .build();

        assertFalse(userMealPlanner.isSaved());
    }
}

