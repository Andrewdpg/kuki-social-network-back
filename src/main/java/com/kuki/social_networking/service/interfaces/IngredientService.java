package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.Ingredient;
import com.kuki.social_networking.request.AddIngredientRequest;
import com.kuki.social_networking.request.UpdateIngredientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Interface for managing the ingredients of the recipes in the application
 */
public interface IngredientService {

    /**
     * creates an ingredient in the app
     *
     * @param ingredient The ingredient data to be created
     * @return The created Ingredient
     * */
    Ingredient createIngredient(AddIngredientRequest ingredient);

    /**
     * Deletes a specified ingredient from the app
     *
     * @param id the ingredient to be deleted
     */
    void deleteIngredient(UUID id);

    /**
     * Finds all the ingredients that match with the name
     *
     * @param name name to be searched in the ingredients
     * @return a list with all the matches in the search
     */
    List<Ingredient> findIngredientMatchByName(String name);

    /**
     * Finds an ingredient by its id
     *
     * @param id the id of the ingredient to be found
     * @return the ingredient with the specified id
     */
    Ingredient findIngredientById(UUID id);

    /**
     * Updates the ingredient with the new data
     *
     * @param request the new data for the ingredient
     * @return the updated ingredient
     */
    Ingredient updateRecipe(UUID id, UpdateIngredientRequest request);

    /**
     * Finds all the ingredients in the app
     *
     * @return a list with all the ingredients in the app
     */
    Page<Ingredient> findAllOrderByName(Pageable pageable);
}
