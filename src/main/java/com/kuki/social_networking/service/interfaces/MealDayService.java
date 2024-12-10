package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.MealDay;
import com.kuki.social_networking.request.CreateMealDayRequest;
import com.kuki.social_networking.request.SearchMealDaysRequest;
import com.kuki.social_networking.request.UpdateMealDayRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

/**
 * Interface for managing meal days in the application.
 */
public interface MealDayService {

    /**
     * Retrieves the meal days for a specific meal planner.
     *
     * @param plannerId The ID of the meal planner.
     * @param request The search request.
     * @return A page of meal days.
     */
    Page<MealDay> getMealDays(UserDetails user, UUID plannerId, SearchMealDaysRequest request);

    /**
     * Creates a new meal day.
     *
     * @param plannerId The ID of the meal planner.
     * @param request The create request.
     * @return The created meal day.
     */
    MealDay createMealDay(UserDetails user, UUID plannerId, CreateMealDayRequest request);

    /**
     * Updates an existing meal day.
     *
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     * @param request The update request.
     * @return The updated meal day.
     */
    MealDay updateMealDay(UserDetails user, UUID plannerId, int day, UpdateMealDayRequest request);

    /**
     * Deletes an existing meal day.
     *
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     */
    void deleteMealDay(UserDetails user, UUID plannerId, int day);
}
