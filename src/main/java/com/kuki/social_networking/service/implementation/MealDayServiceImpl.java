package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.exception.MealDayNotFoundException;
import com.kuki.social_networking.model.MealDay;
import com.kuki.social_networking.model.MealDayId;
import com.kuki.social_networking.model.MealPlanner;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.repository.MealDayRepository;
import com.kuki.social_networking.repository.MealPlannerRepository;
import com.kuki.social_networking.repository.RecipeRepository;
import com.kuki.social_networking.request.CreateMealDayRequest;
import com.kuki.social_networking.request.SearchMealDaysRequest;
import com.kuki.social_networking.request.UpdateMealDayRequest;
import com.kuki.social_networking.service.interfaces.MealDayService;
import com.kuki.social_networking.util.crud.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the MealDayService interface.
 */
@Service
@RequiredArgsConstructor
public class MealDayServiceImpl implements MealDayService {

    private final MealDayRepository mealDayRepository;
    private final MealPlannerRepository mealPlannerRepository;
    private final RecipeRepository recipeRepository;

    
    /**
     * Retrieves the meal days for a specific meal planner.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param request The search request.
     * @return A page of meal days.
     */
    @Override
    public Page<MealDay> getMealDays(UserDetails user, UUID plannerId, SearchMealDaysRequest request) {
        return mealDayRepository.searchMealDays(plannerId, user.getUsername(), request, request.pageable());
    }

    /**
     * Creates a new meal day.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param request The create request.
     * @return The created meal day.
     */
    @Override
    public MealDay createMealDay(UserDetails user, UUID plannerId, CreateMealDayRequest request) {
        MealPlanner mealPlanner = mealPlannerRepository.findById(plannerId)
            .orElseThrow(() -> new MealDayNotFoundException("Meal planner not found"));

        if (mealDayRepository.existsById(plannerId, request.getDay())) {
            throw new IllegalArgumentException("Meal day already exists");
        }

        List<Recipe> recipes = recipeRepository.findAccessibleRecipesByIds(user.getUsername(), request.getRecipeIds());

        MealDay mealDay = MealDay.builder()
            .id(new MealDayId())
            .description(request.getDescription())
            .mealPlanner(mealPlanner)
            .recipes(recipes)
            .build();

        mealDay.getId().setDay(request.getDay());
        mealDay.getId().setPlannerId(plannerId);

        return mealDayRepository.save(mealDay);
    }

    /**
     * Updates an existing meal day.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     * @param request The update request.
     * @return The updated meal day.
     */
    @Override
    public MealDay updateMealDay(UserDetails user, UUID plannerId, int day, UpdateMealDayRequest request) {
        MealDay mealDay = mealDayRepository.findOwnedMealDayById(plannerId, day, user.getUsername())
            .orElseThrow(() -> new MealDayNotFoundException("Meal day not found"));

        if (request.getDescription() != null) mealDay.setDescription(request.getDescription());
        if (request.getNewDay() != null) {
            mealDayRepository.findOwnedMealDayById(plannerId, request.getNewDay(), user.getUsername())
                .ifPresent(meal -> {
                    throw new IllegalArgumentException("Meal day already exists");
                });
            mealDay.getId().setDay(request.getNewDay());
        }

        if (request.getRecipeIds() != null) {
            List<UUID> toAdd = Operation.filterByOperation(request.getRecipeIds(), Operation.ADD);
            if(!toAdd.isEmpty()) {
                List<Recipe> recipes = recipeRepository.findAccessibleRecipesByIds(user.getUsername(), toAdd);
                mealDay.getRecipes().addAll(recipes);
            }

            List<UUID> toDelete = Operation.filterByOperation(request.getRecipeIds(), Operation.DELETE);
            if (!toDelete.isEmpty()) {
                mealDay.getRecipes().removeIf(recipe -> toDelete.contains(recipe.getId()));
            }
        }

        return mealDayRepository.save(mealDay);
    }

    /**
     * Deletes an existing meal day.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     */
    @Override
    public void deleteMealDay(UserDetails user, UUID plannerId, int day) {
        MealDay mealDay = mealDayRepository.findOwnedMealDayById(plannerId, day, user.getUsername())
            .orElseThrow(() -> new MealDayNotFoundException("Meal day not found"));

        mealDayRepository.delete(mealDay);
    }
}