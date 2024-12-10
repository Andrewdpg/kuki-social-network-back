package com.kuki.social_networking.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.kuki.social_networking.model.MealDay;
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
public class MealDayRepositoryTest {

    @Autowired
    private MealDayRepository mealDayRepository;

    @Autowired
    private MealPlannerRepository mealPlannerRepository;

    /**
     * Tests the creation of a MealDay entity.
     * This test ensures that:
     * 1. A MealDay can be successfully created and saved to the database.
     * 2. The saved MealDay has the correct attributes (day, description, associated MealPlanner).
     * 3. The saved MealDay is assigned a non-null ID.
     */
    @Test
    @Transactional
    public void testCreateMealDay() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .build();
        mealPlannerRepository.save(mealPlanner);

        MealDay mealDay = MealDay.builder()
                .day(1)
                .description("Monday meals")
                .mealPlanner(mealPlanner)
                .recipes(new ArrayList<>())
                .build();
        mealDay = mealDayRepository.save(mealDay);

        assertNotNull(mealDay);
        assertNotNull(mealDay.getId());
        assertEquals(1, mealDay.getDay());
        assertEquals("Monday meals", mealDay.getDescription());
        assertEquals(mealPlanner, mealDay.getMealPlanner());
    }

    /**
     * Tests the retrieval of a MealDay entity by its ID.
     * This test verifies that:
     * 1. A MealDay can be successfully saved to the database.
     * 2. The saved MealDay can be retrieved using its ID.
     * 3. The retrieved MealDay has the correct ID.
     */
    @Test
    @Transactional
    public void testFindMealDayById() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .build();
        mealPlannerRepository.save(mealPlanner);

        MealDay mealDay = MealDay.builder()
                .day(1)
                .description("Monday meals")
                .mealPlanner(mealPlanner)
                .recipes(new ArrayList<>())
                .build();
        mealDay = mealDayRepository.save(mealDay);

        Optional<MealDay> foundMealDay = mealDayRepository.findById(mealDay.getId());
        assertTrue(foundMealDay.isPresent());
        assertEquals(mealDay.getId(), foundMealDay.get().getId());
    }

    /**
     * Tests the update operation on a MealDay entity.
     * This test ensures that:
     * 1. A MealDay can be successfully created and saved to the database.
     * 2. The MealDay's attributes can be updated.
     * 3. The updated MealDay is correctly persisted in the database.
     * 4. The retrieved MealDay reflects the updated changes.
     */
    @Test
    @Transactional
    public void testUpdateMealDay() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .build();
        mealPlannerRepository.save(mealPlanner);

        MealDay mealDay = MealDay.builder()
                .day(1)
                .description("Monday meals")
                .mealPlanner(mealPlanner)
                .recipes(new ArrayList<>())
                .build();
        mealDay = mealDayRepository.save(mealDay);

        mealDay.setDescription("Updated Monday meals");
        mealDay = mealDayRepository.save(mealDay);

        Optional<MealDay> updatedMealDay = mealDayRepository.findById(mealDay.getId());
        assertTrue(updatedMealDay.isPresent());
        assertEquals("Updated Monday meals", updatedMealDay.get().getDescription());
    }

    /**
     * Tests the deletion of a MealDay entity.
     * This test verifies that:
     * 1. A MealDay can be successfully created and saved to the database.
     * 2. The MealDay can be deleted from the database.
     * 3. After deletion, the MealDay no longer exists in the database.
     */
    @Test
    @Transactional
    public void testDeleteMealDay() {
        MealPlanner mealPlanner = MealPlanner.builder()
                .name("Weekly Plan")
                .description("A balanced meal plan for the week")
                .build();
        mealPlannerRepository.save(mealPlanner);

        MealDay mealDay = MealDay.builder()
                .day(1)
                .description("Monday meals")
                .mealPlanner(mealPlanner)
                .recipes(new ArrayList<>())
                .build();
        mealDay = mealDayRepository.save(mealDay);

        UUID mealDayId = mealDay.getId();
        mealDayRepository.delete(mealDay);

        Optional<MealDay> deletedMealDay = mealDayRepository.findById(mealDayId);
        assertTrue(deletedMealDay.isEmpty());
    }
}