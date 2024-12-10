package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.request.CreateRecipeRequest;
import com.kuki.social_networking.request.FeedRequest;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.request.SearchRecipesRequest;
import com.kuki.social_networking.request.UpdateRecipeRequest;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

/**
 * Interface for managing recipes in the application.
 */
public interface RecipeService {

    /**
     * Creates a new recipe.
     *
     * @param user The authenticated user creating the recipe.
     * @param request The recipe data to be created.
     * @return The created recipe with updated information (e.g., ID, timestamps).
     */
    Recipe createRecipe(UserDetails user, CreateRecipeRequest request);

    /**
     * Updates an existing recipe.
     *
     * @param user The authenticated user updating the recipe.
     * @param request The updated recipe information.
     * @return The updated recipe.
     */
    Recipe updateRecipe(UserDetails user, UUID id, UpdateRecipeRequest request);

    /**
     * Deletes a recipe from the system.
     *
     * @param user The authenticated user deleting the recipe.
     * @param recipeId The ID of the recipe to be deleted.
     */
    void deleteRecipe(UserDetails user, UUID recipeId);

    /**
     * Retrieves a recipe by its ID.
     *
     * @param user The authenticated user requesting the recipe.
     * @param recipeId The ID of the recipe.
     * @return The recipe with the specified ID.
     */
    Recipe getRecipeById(UserDetails user, UUID recipeId);

    /**
     * Searches for recipes based on title, ingredients, and tags.
     *
     * @param user The authenticated user searching for recipes.
     * @param request The search criteria.
     * @return A list of recipes that match the search criteria.
     */
    Page<Recipe> searchRecipes(UserDetails user, SearchRecipesRequest request);

    /**
     * Retrieves a recipe by its ID.
     *
     * @param user The authenticated user requesting the recipe.
     * @param recipeId The ID of the recipe.
     * @return The recipe with the specified ID.
     */
    Recipe getRecipeOwnedByUser(UserDetails user, UUID recipeId);

    /**
     * Likes a recipe.
     *
     * @param user The user liking the recipe.
     * @param recipeId The ID of the recipe to be liked.
     */
    User likeRecipe(UserDetails user, UUID recipeId);

    /**
     * Removes a like from a recipe.
     *
     * @param user The user unliking the recipe.
     * @param recipeId The ID of the recipe to be unliked.
     */
    void unlikeRecipe(UserDetails user, UUID recipeId);

    /**
     * Saves a recipe to a user's saved list.
     *
     * @param user The user saving the recipe.
     * @param recipeId The ID of the recipe to be saved.
     */
    void saveRecipe(UserDetails user, UUID recipeId);

    /**
     * Removes a recipe from a user's saved list.
     *
     * @param user The user removing the recipe.
     * @param recipeId The ID of the recipe to be removed.
     */
    void unsaveRecipe(UserDetails user, UUID recipeId);

    /**
     * Retrieves all recipes liked by a specific user.
     *
     * @param user The user object.
     * @return A list of recipes liked by the user.
     */
    Page<Recipe> getSavedRecipes(UserDetails user, PageableRequest request);

    /**
     * Retrieves all recipes created by a specific user.
     *
     * @param user The user object.
     * @param owner The username of the user whose recipes are to be retrieved.
     * @return A list of recipes created by the user.
     */
    Page<Recipe> getRecipesByUser(UserDetails user, String owner, PageableRequest request);

    /**
     * Retrieves a recommended feed for a user using a heuristic approach.
     * The recommended feed is based on the user's liked recipes, saved recipes, and other factors.
     *
     * @param user The user object.
     * @return A list of recommended recipes for the user.
     */
    public Page<Recipe> getRecommendedFeed(UserDetails user, FeedRequest request);

    /**
     * Checks if a user likes a recipe.
     *
     * @param user The user object.
     * @return True if the user likes the recipe, false otherwise.
     */
    boolean userLikes(CustomUserDetails user, Recipe recipe);

    /**
     * Checks if a user has saved a recipe.
     *
     * @param user The user object.
     * @return True if the user has saved the recipe, false otherwise.
     */
    boolean userSaved(CustomUserDetails user, Recipe recipe);

    /**
     * Retrieves a list of users who liked a recipe.
     *
     * @param user The user object.
     * @param recipeId The ID of the recipe.
     * @return A list of users who liked the recipe.
     */
    Page<User> getLikesOf(CustomUserDetails user, String recipeId, PageableRequest request);
}
