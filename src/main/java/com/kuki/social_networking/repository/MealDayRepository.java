package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.MealDay;

import java.util.Optional;
import java.util.UUID;

import com.kuki.social_networking.model.MealDayId;
import com.kuki.social_networking.request.SearchMealDaysRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link MealDay} entities.
 * Extends {@link JpaRepository } for basic CRUD operations.
 */
@Repository
public interface MealDayRepository extends JpaRepository<MealDay, MealDayId> {

    /**
     * Searches for meal days based on the provided SearchMealDaysRequest.
     *
     * @param plannerId the ID of the meal planner.
     * @param username the username of the user.
     * @param request the request object containing the search criteria for meal days.
     * @param pageable the pagination information.
     * @return a page of meal days that match the search criteria.
     */
    @Query("SELECT md FROM MealDay md  LEFT JOIN md.mealPlanner.userMealPlanners ump " +
        "WHERE md.mealPlanner.id = :plannerId " +
        "AND (md.mealPlanner.isPublic = true OR ump.user.username = :username) " +
        "AND (:#{#request.fromDay} IS NULL OR md.id.day >= :#{#request.fromDay}) " +
        "AND (:#{#request.toDay} IS NULL OR md.id.day <= :#{#request.toDay})")
    Page<MealDay> searchMealDays(@Param("plannerId") UUID plannerId,
                                 @Param("username") String username,
                                 @Param("request") SearchMealDaysRequest request,
                                 Pageable pageable);

    /**
     * Retrieves a meal day by its ID.
     *
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     * @param username The username of the user.
     * @return The meal day with the specified ID.
     */
    @Query("SELECT md FROM MealDay md LEFT JOIN md.mealPlanner.userMealPlanners ump WHERE md.id.plannerId = :plannerId AND md.id.day = :day AND (md.mealPlanner.isPublic = true OR ump.user.username = :username)")
    Optional<MealDay> findAccessibleMealDayById(@Param("plannerId") UUID plannerId, @Param("day") int day, @Param("username") String username);

    /**
     * Retrieves a meal day by its ID.
     *
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     * @param username The username of the user.
     * @return The meal day with the specified ID.
     */
    @Query("SELECT md FROM MealDay md LEFT JOIN md.mealPlanner.userMealPlanners ump WHERE md.id.plannerId = :plannerId AND md.id.day = :day AND ump.user.username = :username AND ump.isOwner = true")
    Optional<MealDay> findOwnedMealDayById(@Param("plannerId") UUID plannerId, @Param("day") int day, @Param("username") String username);

    /**
     * Checks if a meal day with the specified ID exists.
     *
     * @param plannerId The ID of the meal planner.
     * @param day The day of the meal day.
     * @return True if a meal day with the specified ID exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(md) > 0 THEN TRUE ELSE FALSE END FROM MealDay md WHERE md.id.plannerId = :plannerId AND md.id.day = :day")
    boolean existsById(UUID plannerId, int day);
}
