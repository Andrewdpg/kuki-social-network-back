package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.Ingredient;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;

/**
 * Repository for managing {@link Ingredient} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for complex queries.
 */
public interface IngredientRepository extends JpaRepository<Ingredient, UUID>, JpaSpecificationExecutor<Ingredient> {

    /**
     * Finds ingredients by their name, ignoring case.
     *
     * @param name the name of the ingredient.
     * @return a list of ingredients that match the specified name.
     */
    List<Ingredient> findByNameContainingIgnoreCase(String name);

    /**
     * Finds the top 10 ingredients by their name, ignoring case.
     *
     * @param name the name of the ingredient.
     * @return a list of the top 10 ingredients that match the specified name.
     */
    List<Ingredient> findTop10ByNameContainingIgnoreCase(String name);

    /**
     * Finds all ingredients sorted by name.
     *
     * @param pageable the pagination information.
     * @return a page of ingredients sorted by name.
     */
    Page<Ingredient> findAllByOrderByNameAsc(Pageable pageable);

    /**
     * Finds ingredients associated with a specific recipe by the recipe's ID.
     *
     * @param recipeId the ID of the recipe.
     * @return a list of ingredients used in the specified recipe.
     */
    @Query("SELECT i FROM Ingredient i JOIN i.recipes r WHERE r.recipe.id = :recipeId")
    List<Ingredient> findIngredientsByRecipeId(@Param("recipeId") UUID recipeId);
}
