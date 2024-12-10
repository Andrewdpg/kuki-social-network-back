package com.kuki.social_networking.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kuki.social_networking.exception.MealPlannerNotFoundException;
import com.kuki.social_networking.model.MealPlanner;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.UserMealPlanner;
import com.kuki.social_networking.repository.MealPlannerRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.request.CreatePlannerRequest;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.request.SearchPlannerRequest;
import com.kuki.social_networking.request.UpdatePlannerRequest;
import com.kuki.social_networking.service.interfaces.MealPlannerService;
import com.kuki.social_networking.util.validator.FieldValidator;

import lombok.RequiredArgsConstructor;

/**
 * Service for meal planning.
 */
@Service
@RequiredArgsConstructor
public class MealPlannerServiceImpl implements MealPlannerService {

    private final UserRepository userRepository;
    private final MealPlannerRepository mealPlannerRepository;

    /**
     * Creates a meal plan for a user.
     *
     * @param user The user to create the meal plan for.
     * @param request The request to create the meal plan.
     * @return The created meal plan.
     */
    @Override
    public MealPlanner createMealPlan(UserDetails user, CreatePlannerRequest request) {
        if(user == null) {
            throw new IllegalArgumentException("User must be authenticated");
        }

        User owner = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        MealPlanner planner = mealPlannerRepository.save(MealPlanner.builder()
            .name(FieldValidator.validateString(request.getName()))
            .description(FieldValidator.validateString(request.getDescription()))
            .isPublic(false)
            .build());

        List<UserMealPlanner> userMealPlanners = new ArrayList<>();
        userMealPlanners.add(UserMealPlanner.builder()
            .user(owner)
            .mealPlanner(planner)
            .isOwner(true)
            .build());

        planner.setUserMealPlanners(userMealPlanners);

        return mealPlannerRepository.save(planner);
    }

    /**
     * Retrieves the meal plan for a user.
     *
     * @param user The user to retrieve the meal plan for.
     * @param plannerId The id of the meal plan to retrieve.
     * @return The meal plan for the user.
     */
    @Override
    public MealPlanner getMealPlan(UserDetails user, UUID plannerId) {
        return mealPlannerRepository.findMealPlannerByID(user.getUsername(), plannerId)
            .orElseThrow(() -> new MealPlannerNotFoundException("Meal planner not found"));
    }

    /**
     * Retrieves the meal plans for a user.
     *
     * @param user The user to retrieve the meal plans for.
     * @param request The request to retrieve the meal plans.
     * @return The meal plans for the user.
     */
    @Override
    public Page<MealPlanner> getMealPlans(UserDetails user, SearchPlannerRequest request) {
        return mealPlannerRepository.findAllByNameAndUser(user.getUsername(), request.getName(), request.pageable());
    }

    /**
     * Updates the meal plan for a user.
     *
     * @param user    The user to update the meal plan for.
     * @param plannerId The id of the meal plan to update.
     * @param request The request to update the meal plan.
     * @return The updated meal plan.
     */
    @Override
    public MealPlanner updateMealPlan(UserDetails user, UUID plannerId, UpdatePlannerRequest request) {
        if (user == null) {
            throw new IllegalArgumentException("User must be authenticated");
        }

        MealPlanner existingPlanner = mealPlannerRepository.findMealPlannerByIdWithOwnership(user.getUsername(), plannerId)
            .orElseThrow(() -> new MealPlannerNotFoundException("Meal planner not found"));

        if (request.getName() != null) existingPlanner.setName(FieldValidator.validateString(request.getName()));
        if (request.getDescription() != null) existingPlanner.setDescription(FieldValidator.validateString(request.getDescription()));
        if (request.getIsPublic() != null) existingPlanner.setIsPublic(request.getIsPublic());

        return mealPlannerRepository.save(existingPlanner);
    }

    /**
     * Deletes the meal plan for a user.
     *
     * @param user The user to delete the meal plan for.
     * @param plannerId The id of the meal plan to delete.
     */
    @Override
    public void deleteMealPlan(UserDetails user, UUID plannerId) {
        if (user == null) {
            throw new IllegalArgumentException("User must be authenticated");
        }

        MealPlanner planner = mealPlannerRepository.findMealPlannerByIdWithOwnership(user.getUsername(), plannerId)
            .orElseThrow(() -> new MealPlannerNotFoundException("Meal planner not found"));

        mealPlannerRepository.delete(planner);
    }

    /**
     * Retrieves the recipes in the meal plan for a user.
     *
     * @param userId The user's id.
     * @param request The request to retrieve the recipes.
     * @return The recipes in the user's meal plan.
     */
    @Override
    public Page<Recipe> getRecipesInMealPlan(String userId, PageableRequest request) {
        return null;
    }
}