package com.kuki.social_networking.controller.rest;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kuki.social_networking.exception.MealDayNotFoundException;
import com.kuki.social_networking.mapper.MealDayMapper;
import com.kuki.social_networking.model.MealDay;
import com.kuki.social_networking.request.CreateMealDayRequest;
import com.kuki.social_networking.request.SearchMealDaysRequest;
import com.kuki.social_networking.request.UpdateMealDayRequest;
import com.kuki.social_networking.response.MealDayResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.MealDayService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

/**
 * Controller for managing meal days.
 */
@RestController
@RequestMapping("/api/v1/planner/{plannerId}/day")
@RequiredArgsConstructor
public class MealDayController {

    private final MealDayService mealDayService;
    private final MealDayMapper mealDayMapper;

    /**
     * Retrieves the meal days for a specific meal planner.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param request The search request.
     * @return A response entity containing a page of meal days.
     */
    @GetMapping
    public ResponseEntity<Page<MealDayResponse>> getMealDays(@AuthenticatedUser CustomUserDetails user, @PathVariable UUID plannerId, @ModelAttribute SearchMealDaysRequest request) {
        Page<MealDay> mealDays = mealDayService.getMealDays(user, plannerId, request);
        return ResponseEntity.ok(mealDays.map(mealDayMapper::toMealDayResponse));
    }

    /**
     * Creates a new meal day.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param createMealDayRequest The create request.
     * @return A response entity containing the created meal day.
     */
    @PostMapping
    public ResponseEntity<MealDayResponse> createMealDay(@AuthenticatedUser CustomUserDetails user, @PathVariable UUID plannerId, @RequestBody CreateMealDayRequest createMealDayRequest) {
        MealDay mealDay = mealDayService.createMealDay(user, plannerId, createMealDayRequest);
        return ResponseEntity.ok(mealDayMapper.toMealDayResponse(mealDay));
    }

    /**
     * Updates an existing meal day.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     * @param updateMealDayRequest The update request.
     * @return A response entity containing the updated meal day.
     */
    @PutMapping("/{day}")
    public ResponseEntity<MealDayResponse> updateMealDay(@AuthenticatedUser CustomUserDetails user, @PathVariable UUID plannerId, @PathVariable int day, @RequestBody UpdateMealDayRequest updateMealDayRequest) {
        MealDay mealDay = mealDayService.updateMealDay(user, plannerId, day, updateMealDayRequest);
        return ResponseEntity.ok(mealDayMapper.toMealDayResponse(mealDay));
    }

    /**
     * Deletes an existing meal day.
     *
     * @param user The authenticated user.
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     * @return A response entity with no content.
     */
    @DeleteMapping("/{day}")
    public ResponseEntity<Void> deleteMealDay(@AuthenticatedUser CustomUserDetails user, @PathVariable UUID plannerId, @PathVariable int day) {
        mealDayService.deleteMealDay(user, plannerId, day);
        return ResponseEntity.noContent().build();
    }

    // Exception Handlers

    /**
     * Handles MealDayNotFoundException.
     *
     * @param e The exception.
     * @return A response entity with status 404 (Not Found).
     */
    @ExceptionHandler(MealDayNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleMealDayNotFoundException(MealDayNotFoundException e) {
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
