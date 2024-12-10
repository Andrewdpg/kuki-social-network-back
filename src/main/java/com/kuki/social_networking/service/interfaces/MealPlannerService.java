package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.MealPlanner;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.request.CreatePlannerRequest;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.request.SearchPlannerRequest;
import com.kuki.social_networking.request.UpdatePlannerRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

/**
 * Interface for meal planning services.
 */
public interface MealPlannerService {

    /**
     * Creates a meal plan for a user.
     *
     * @param user The user to create the meal plan for.
     * @param request The request to create the meal plan.
     * @return The created meal plan.
     */
    MealPlanner createMealPlan(UserDetails user, CreatePlannerRequest request);

    /**
     * Retrieves the meal plan for a user.
     *
     * @param user The user to retrieve the meal plan for.
     * @param plannerId The id of the meal plan to retrieve.
     * @return The meal plan for the user.
     */
    MealPlanner getMealPlan(UserDetails user, UUID plannerId);

    /**
     * Retrieves the meal plans for a user.
     *
     * @param user The user to retrieve the meal plans for.
     * @param request The request to retrieve the meal plans.
     * @return The meal plans for the user.
     */
    Page<MealPlanner> getMealPlans(UserDetails user, SearchPlannerRequest request);

    /**
     * Updates the meal plan for a user.
     *
     * @param user    The user to update the meal plan for.
     * @param plannerId The id of the meal plan to update.
     * @param request The request to update the meal plan.
     * @return The updated meal plan.
     */
    MealPlanner updateMealPlan(UserDetails user, UUID plannerId, UpdatePlannerRequest request);

    /**
     * Deletes the meal plan for a user.
     *
     * @param user The user to delete the meal plan for.
     * @param plannerId The id of the meal plan to delete.
     */
    void deleteMealPlan(UserDetails user, UUID plannerId);

    /**
     * Retrieves the recipes in the meal plan for a user.
     *
     * @param userId The user's id.
     * @param request The request to retrieve the recipes.
     * @return The recipes in the user's meal plan.
     */
    Page<Recipe> getRecipesInMealPlan(String userId, PageableRequest request);
}
