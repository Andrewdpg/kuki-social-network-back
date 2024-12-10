package com.kuki.social_networking.controller.rest;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kuki.social_networking.exception.MealPlannerNotFoundException;
import com.kuki.social_networking.mapper.MealPlannerMapper;
import com.kuki.social_networking.model.MealPlanner;
import com.kuki.social_networking.request.CreatePlannerRequest;
import com.kuki.social_networking.request.SearchPlannerRequest;
import com.kuki.social_networking.request.UpdatePlannerRequest;
import com.kuki.social_networking.response.MealPlannerResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.MealPlannerService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

/**
 * Controller for managing meal planners.
 */
@RestController
@RequestMapping("/api/v1/planner")
@RequiredArgsConstructor
public class MealPlannerController {

    private final MealPlannerService plannerService;
    private final MealPlannerMapper mealPlannerMapper;

    /**
     * Creates a new meal planner.
     *
     * @param user The authenticated user.
     * @param createPlannerRequest The create request.
     * @return A response entity containing the created meal planner.
     */
    @PostMapping
    public ResponseEntity<MealPlannerResponse> createPlanner(@AuthenticatedUser CustomUserDetails user, @RequestBody CreatePlannerRequest createPlannerRequest) {
        MealPlanner planner = plannerService.createMealPlan(user, createPlannerRequest);
        return ResponseEntity.ok(mealPlannerMapper.toMealPlannerResponse(planner));
    }

    /**
     * Searches for meal planners.
     *
     * @param user The authenticated user.
     * @param searchPlannerRequest The search request.
     * @return A response entity containing a page of meal planners.
     */
    @GetMapping
    public ResponseEntity<Page<MealPlannerResponse>> searchPlanner(@AuthenticatedUser CustomUserDetails user, SearchPlannerRequest searchPlannerRequest) {
        Page<MealPlanner> planners = plannerService.getMealPlans(user, searchPlannerRequest);
        return ResponseEntity.ok(planners.map(mealPlannerMapper::toMealPlannerResponse));
    }

    /**
     * Retrieves a specific meal planner.
     *
     * @param user The authenticated user.
     * @param id The ID of the meal planner.
     * @return A response entity containing the meal planner.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealPlannerResponse> getPlanner(@AuthenticatedUser CustomUserDetails user, @PathVariable String id) {
        MealPlanner planner = plannerService.getMealPlan(user, UUID.fromString(id));
        return ResponseEntity.ok(mealPlannerMapper.toMealPlannerResponse(planner));
    }

    /**
     * Updates an existing meal planner.
     *
     * @param user The authenticated user.
     * @param id The ID of the meal planner.
     * @param updatePlannerRequest The update request.
     * @return A response entity containing the updated meal planner.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MealPlannerResponse> updatePlanner(@AuthenticatedUser CustomUserDetails user, @PathVariable String id, @RequestBody UpdatePlannerRequest updatePlannerRequest) {
        MealPlanner planner = plannerService.updateMealPlan(user, UUID.fromString(id), updatePlannerRequest);
        return ResponseEntity.ok(mealPlannerMapper.toMealPlannerResponse(planner));
    }

    /**
     * Deletes an existing meal planner.
     *
     * @param user The authenticated user.
     * @param id The ID of the meal planner.
     * @return A response entity with no content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanner(@AuthenticatedUser CustomUserDetails user, @PathVariable String id) {
        plannerService.deleteMealPlan(user, UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    // Exception Handlers

    /**
     * Handles MealPlannerNotFoundException.
     *
     * @param e The exception.
     * @return A response entity with status 404 (Not Found).
     */
    @ExceptionHandler(MealPlannerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleMealPlannerNotFoundException(MealPlannerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * Handles IllegalArgumentException.
     *
     * @param e The exception.
     * @return A response entity with status 400 (Bad Request).
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}