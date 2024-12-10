package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.MealPlanner;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

/**
 * Repository for managing {@link MealPlanner} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for complex queries.
 */
public interface MealPlannerRepository extends JpaRepository<MealPlanner, UUID>, JpaSpecificationExecutor<MealPlanner> {

    /**
     * Finds meal planners by their name, ignoring case.
     *
     * @param name the name of the meal planner.
     * @return a list of meal planners that match the specified name.
     */
    List<MealPlanner> findByNameContainingIgnoreCase(String name);

    /**
     * Search meal planners by their name (like) and the username of the user associated with them.
     *
     * @param username the username of the user.
     * @param name the name of the meal planner.
     * @param pageable the pagination information.
     * @return a list of meal planners that match the specified name and are associated with the specified user.
     */
    @Query("SELECT mp FROM MealPlanner mp  LEFT JOIN mp.userMealPlanners ump WHERE (mp.isPublic = true OR ump.user.username = :username) AND (:name IS NULL OR mp.name ILIKE %:name%)")
    Page<MealPlanner> findAllByNameAndUser(@Param("username") String username, @Param("name") String name, Pageable pageable);

    /**
     * Finds a meal planner by its id and validates ownership for the username of the user associated with it.
     *
     * @param username the username of the user.
     * @param mealPlannerId the id of the meal planner.
     * @return the meal planner with the specified id and associated with the specified user as owner.
     */
    @Query("SELECT ump.mealPlanner FROM UserMealPlanner ump WHERE ump.mealPlanner.id = :mealPlannerId AND ump.user.username = :username AND ump.isOwner = true")
    Optional<MealPlanner> findMealPlannerByIdWithOwnership(@Param("username") String username, @Param("mealPlannerId") UUID mealPlannerId);

    /**
     * Finds a meal planner by its id and validates permissions for the username of the user associated with it.
     *
     * @param username the username of the user.
     * @param mealPlannerId the id of the meal planner.
     * @return the meal planner with the specified id and associated with the specified user.
     */
    @Query("SELECT mp FROM MealPlanner mp LEFT JOIN mp.userMealPlanners ump WHERE mp.id = :mealPlannerId AND (ump.user.username = :username OR mp.isPublic = true)")
    Optional<MealPlanner> findMealPlannerByID(@Param("username") String username, @Param("mealPlannerId") UUID mealPlannerId);

}

