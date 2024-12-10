package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.Recipe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for managing {@link Recipe} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for complex queries.
 */
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {

    /**
     * Retrieves a recipe by its ID.
     *
     * @param recipeId The ID of the recipe.
     * @return The recipe with the specified ID.
     */
    @Query("SELECT r FROM Recipe r WHERE r.id = :recipeId AND (r.publishDate IS NOT NULL OR r.recipeOwner.username = :username)")
    Optional<Recipe> findAccessibleRecipeById(UUID recipeId, String username);

    /**
     * Retrieves a list of recipes by their IDs.
     *
     * @param recipeIds The list of recipe IDs.
     * @param username The username of the user.
     * @return A list of recipes with the specified IDs.
     */
    @Query("SELECT r FROM Recipe r WHERE r.id IN :recipeIds AND (r.publishDate IS NOT NULL OR r.recipeOwner.username = :username)")
    List<Recipe> findAccessibleRecipesByIds(@Param("username") String username, @Param("recipeIds") List<UUID> recipeIds);

    /**
     * Finds recipes by their owner.
     * @param owner The owner of the recipe.
     * @param user The user requesting the recipe.
     * @param pageable The pageable object for pagination.
     * @return A page of recipes with the specified owner.
     */
    @Query("SELECT r FROM Recipe r WHERE r.recipeOwner.username = :owner AND (r.publishDate IS NOT NULL OR r.recipeOwner.username = :user)")
    Page<Recipe> findByRecipeOwner(String owner, String user, Pageable pageable);

    /**
     * Finds a recipe by its owner and ID.
     *
     * @param owner The owner of the recipe.
     * @param id The ID of the recipe.
     * @return The recipe with the specified owner and ID.
     */
    @Query("SELECT r FROM Recipe r WHERE (r.recipeOwner.username = :owner OR :owner IN (SELECT u.username FROM User u JOIN u.roles ur WHERE ur.name = 'ADMIN')) AND r.id = :id")
    Optional<Recipe> findByRecipeOwnerAndId(String owner, UUID id);

    /**
     * Checks if a user has liked a recipe.
     *
     * @param username The username of the user.
     * @param recipe The recipe to check.
     * @return True if the user has liked the recipe, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN TRUE ELSE FALSE END FROM Recipe r JOIN r.likes l WHERE l.username = :username AND r = :recipe")
    boolean existsByLikesUsername(String username, Recipe recipe);

    /**
     * Checks if a user has saved a recipe.
     *
     * @param username The username of the user.
     * @param recipe The recipe to check.
     * @return True if the user has saved the recipe, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM Recipe r JOIN r.usersWhoSaved u WHERE u.username = :username AND r = :recipe")
    boolean existsByUsersWhoSavedUsername(String username, Recipe recipe);

    /**
     * Finds recipes saved by a user.
     *
     * @param owner The username of the user.
     * @param pageable The pageable object for pagination.
     * @return A page of recipes saved by the user.
     */
    @Query("SELECT r FROM Recipe r JOIN r.usersWhoSaved u WHERE u.username = :owner")
    Page<Recipe> findRecipesSavedByUser(@Param("owner") String owner, Pageable pageable);
}
