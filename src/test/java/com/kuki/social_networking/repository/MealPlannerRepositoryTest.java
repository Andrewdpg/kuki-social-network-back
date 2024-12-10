package com.kuki.social_networking.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.kuki.social_networking.model.MealPlanner;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

// TODO: Remake test
@Disabled
@SpringBootTest
public class MealPlannerRepositoryTest {

    @Autowired
    private MealPlannerRepository mealPlannerRepository;

    /**
     * Tests the creation of a MealPlanner entity.
     * This test ensures that:
     * 1. A MealPlanner can be successfully created and saved to the database.
     * 2. The saved MealPlanner has a non-null ID.
     * 3. The saved MealPlanner has the correct name, description, and isPublic status.
     */
    @Test
    @Transactional
    public void testCreateMealPlanner() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .isPublic(true)
                .mealDays(new ArrayList<>())
                .userMealPlanners(new ArrayList<>())
                .build();
        mealPlanner = mealPlannerRepository.save(mealPlanner);

        assertNotNull(mealPlanner);
        assertNotNull(mealPlanner.getId());
        assertEquals("Weekly Plan", mealPlanner.getName());
        assertEquals("A balanced meal plan for the week", mealPlanner.getDescription());
        assertTrue(mealPlanner.getIsPublic());
    }

    /**
     * Tests the retrieval of a MealPlanner entity by its ID.
     * This test verifies that:
     * 1. A MealPlanner can be successfully saved to the database.
     * 2. The saved MealPlanner can be retrieved using its ID.
     * 3. The retrieved MealPlanner has the correct ID.
     */
    @Test
    @Transactional
    public void testFindMealPlannerById() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .isPublic(true)
                .mealDays(new ArrayList<>())
                .userMealPlanners(new ArrayList<>())
                .build();
        mealPlanner = mealPlannerRepository.save(mealPlanner);

        Optional<MealPlanner> foundMealPlanner = mealPlannerRepository.findById(mealPlanner.getId());
        assertTrue(foundMealPlanner.isPresent());
        assertEquals(mealPlanner.getId(), foundMealPlanner.get().getId());
    }

    /**
     * Tests the update operation on a MealPlanner entity.
     * This test ensures that:
     * 1. A MealPlanner can be successfully created and saved to the database.
     * 2. The MealPlanner's attributes can be updated.
     * 3. The updated MealPlanner is correctly persisted in the database.
     * 4. The retrieved MealPlanner reflects the updated changes.
     */
    @Test
    @Transactional
    public void testUpdateMealPlanner() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .isPublic(true)
                .mealDays(new ArrayList<>())
                .userMealPlanners(new ArrayList<>())
                .build();
        mealPlanner = mealPlannerRepository.save(mealPlanner);

        mealPlanner.setDescription("Updated description");
        mealPlanner = mealPlannerRepository.save(mealPlanner);

        Optional<MealPlanner> updatedMealPlanner = mealPlannerRepository.findById(mealPlanner.getId());
        assertTrue(updatedMealPlanner.isPresent());
        assertEquals("Updated description", updatedMealPlanner.get().getDescription());
    }

    /**
     * Tests the deletion of a MealPlanner entity.
     * This test verifies that:
     * 1. A MealPlanner can be successfully created and saved to the database.
     * 2. The MealPlanner can be deleted from the database.
     * 3. After deletion, the MealPlanner no longer exists in the database.
     */
    @Test
    @Transactional
    public void testDeleteMealPlanner() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .isPublic(true)
                .mealDays(new ArrayList<>())
                .userMealPlanners(new ArrayList<>())
                .build();
        mealPlanner = mealPlannerRepository.save(mealPlanner);

        UUID mealPlannerId = mealPlanner.getId();
        mealPlannerRepository.delete(mealPlanner);

        Optional<MealPlanner> deletedMealPlanner = mealPlannerRepository.findById(mealPlannerId);
        assertTrue(deletedMealPlanner.isEmpty());
    }
}