package com.kuki.social_networking.service.implementation;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import com.kuki.social_networking.model.*;
import com.kuki.social_networking.repository.*;
import com.kuki.social_networking.service.interfaces.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kuki.social_networking.exception.RecipeNotFoundException;
import com.kuki.social_networking.exception.UserNotFoundException;
import com.kuki.social_networking.request.CreateRecipeRequest;
import com.kuki.social_networking.request.FeedRequest;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.request.SearchRecipesRequest;
import com.kuki.social_networking.request.UpdateRecipeRequest;
import com.kuki.social_networking.service.interfaces.ImageService;
import com.kuki.social_networking.service.interfaces.RecipeService;
import com.kuki.social_networking.util.EntitySpecs;
import static com.kuki.social_networking.util.EntitySpecs.OR;
import static com.kuki.social_networking.util.EntitySpecs.composedQuery;
import static com.kuki.social_networking.util.EntitySpecs.hasAllElements;
import static com.kuki.social_networking.util.EntitySpecs.hasAttribute;
import static com.kuki.social_networking.util.EntitySpecs.joinAttribute;
import static com.kuki.social_networking.util.EntitySpecs.notNull;

import jakarta.persistence.criteria.Join;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Implementation of the RecipeService interface.
 */
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final ImageService imageService;
    private final IngredientRepository ingredientRepository;
    private final TagRepository tagRepository;
    private final MealDayRepository mealDayRepository;
    private final NotificationService notificationService;

    /**
     * Creates a new recipe.
     *
     * @param user The authenticated user creating the recipe.
     * @param request The recipe data to be created.
     * @return The created recipe with updated information (e.g., ID, timestamps).
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public Recipe createRecipe(UserDetails user, CreateRecipeRequest request) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        Country country = countryRepository.findById(request.getCountry()).orElseThrow();

        String imageUrl = null;
        if (request.getImage() != null) {
            try {
                imageService.validateImage(request.getImage());
                imageUrl = imageService.uploadImage(request.getImage());

            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid image file");
            }
        }

        User creator = userRepository.findByUsername(user.getUsername())
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        request.setTags(request.getTags().stream().map((tag)-> tag.trim().replace("\"", "")).toList());
        List<Tag> tags = tagRepository.findByNameIn(request.getTags());

        Recipe recipe = Recipe.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .photoUrl(imageUrl)
            .difficulty(request.getDifficulty())
            .country(country)
            .recipeOwner(creator)
            .tags(tags)
            .build();

        recipe = recipeRepository.save(recipe);

        List<RecipeIngredient> ingredients = new ArrayList<>();
        for (int i = 0; i < request.getIngredients().size(); i++) {
            if (request.getIngredients().get(i).getId() == null || request.getIngredients().get(i).getQuantity() == 0) {
                throw new IllegalArgumentException("Invalid ingredient");
            }

            Ingredient ingredient = ingredientRepository.findById(request.getIngredients().get(i).getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid ingredient"));

            RecipeIngredient recipeIngredient = RecipeIngredient.builder()
                .ingredient(ingredient)
                .recipe(recipe)
                .quantity(request.getIngredients().get(i).getQuantity())
                .build();

            ingredients.add(recipeIngredient);
        }

        recipe.setIngredients(ingredients);

        return recipeRepository.save(recipe);
    }

    /**
     * Updates an existing recipe.
     *
     * @param user The authenticated user updating the recipe.
     * @param request The updated recipe information.
     * @return The updated recipe.
     */
    @Override
    public Recipe updateRecipe(UserDetails user, UUID id, UpdateRecipeRequest request) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        Recipe recipe = getRecipeOwnedByUser(user, id);

        if (request.getTitle() != null) recipe.setTitle(request.getTitle());
        if (request.getDescription() != null) recipe.setDescription(request.getDescription());
        if (request.getPhotoUrl() != null) recipe.setPhotoUrl(request.getPhotoUrl());
        if (request.getDifficulty() != null) recipe.setDifficulty(request.getDifficulty());

        return recipeRepository.save(recipe);
    }

    /**
     * Deletes a recipe from the system.
     *
     * @param user The authenticated user deleting the recipe.
     * @param recipeId The ID of the recipe to be deleted.
     */
    @Override
    public void deleteRecipe(UserDetails user, UUID recipeId) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        Recipe recipe = getRecipeOwnedByUser(user, recipeId);
        for (MealDay mealDay : recipe.getMealDays()) {
            mealDay.getRecipes().remove(recipe);
        }
        mealDayRepository.saveAll(recipe.getMealDays());

        for (Tag tag : recipe.getTags()) {
            tag.getRecipes().remove(recipe);
        }
        tagRepository.saveAll(recipe.getTags());

        for (User temp : recipe.getLikes()) {
            temp.getLikes().remove(recipe);
        }
        userRepository.saveAll(recipe.getLikes());

        for (User temp : recipe.getUsersWhoSaved()) {
            temp.getSavedRecipes().remove(recipe);
        }
        userRepository.saveAll(recipe.getUsersWhoSaved());

        recipeRepository.delete(recipe);
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param user The authenticated user requesting the recipe.
     * @param recipeId The ID of the recipe.
     * @return The recipe with the specified ID.
     */
    @Override
    public Recipe getRecipeById(UserDetails user, UUID recipeId) {
        String username = user == null ? null : user.getUsername();
        return recipeRepository.findAccessibleRecipeById(recipeId, username)
                        .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
    }

    /**
     * Searches for recipes based on title, ingredients, and tags.
     *
     * @param request The search criteria.
     * @return A list of recipes that match the search criteria.
     */
    @Override
    public Page<Recipe> searchRecipes(UserDetails user, SearchRecipesRequest request) {
        String username = user == null ? null : user.getUsername();
        Specification<Recipe> spec = EntitySpecs.<String, Recipe>hasAttributeLike("title", request.getTitle())
            .and(joinAttribute("country","code",request.getCountry()))
            .and(hasAttribute("difficulty", request.getDifficulty()))
            .and(hasAttribute("estimatedTime", request.getEstimatedTime()))
            .and(hasAllElements("ingredients", "ingredient", "id", request.getIngredients()))
            .and(hasAllElements("tags", "name", request.getTags()))
            .and(composedQuery(OR, notNull("publishDate"),joinAttribute("recipeOwner", "username", username)));
        return recipeRepository.findAll(spec, request.pageable());
    }

    @Override
    public Recipe getRecipeOwnedByUser(UserDetails user, UUID recipeId) {
        String username = user == null ? null : user.getUsername();
        return recipeRepository.findByRecipeOwnerAndId(username, recipeId)
            .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
    }

    /**
     * Likes a recipe.
     *
     * @param user The user liking the recipe.
     * @param recipeId The ID of the recipe to be liked.
     */
    @Override
    public User likeRecipe(UserDetails user, UUID recipeId) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }
        Recipe recipe = getRecipeById(user, recipeId);

        User liker = userRepository.findByUsername(user.getUsername())
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        recipe.getLikes().add(liker);

        recipeRepository.save(recipe);

        notificationService.sendNotification(
                recipe.getRecipeOwner().getUsername(),
                "You have a new like on your recipe \"" + recipe.getTitle() + "\"",
                "url"
        );

        return liker;
    }

    /**
     * Removes a like from a recipe.
     *
     * @param user The user unliking the recipe.
     * @param recipeId The ID of the recipe to be unliked.
     */
    @Override
    public void unlikeRecipe(UserDetails user, UUID recipeId) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        Recipe recipe = getRecipeById(user, recipeId);
        recipe.getLikes().removeIf(u -> u.getUsername().equals(user.getUsername()));

        recipeRepository.save(recipe);
    }

    /**
     * Saves a recipe to a user's saved list.
     *
     * @param user The user saving the recipe.
     * @param recipeId The ID of the recipe to be saved.
     */
    @Override
    public void saveRecipe(UserDetails user, UUID recipeId) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        User saver = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        recipe.getUsersWhoSaved().add(saver);
        saver.getSavedRecipes().add(recipe);

        recipeRepository.save(recipe);
        userRepository.save(saver);
    }

    /**
     * Removes a recipe from a user's saved list.
     *
     * @param user The user removing the recipe.
     * @param recipeId The ID of the recipe to be removed.
     */
    @Override
    public void unsaveRecipe(UserDetails user, UUID recipeId) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        User saver = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        recipe.getUsersWhoSaved().removeIf(u -> u.getUsername().equals(user.getUsername()));
        saver.getSavedRecipes().removeIf(r -> r.getId().equals(recipeId));

        recipeRepository.save(recipe);
        userRepository.save(saver);
    }

    /**
     * Retrieves a list of recipes liked by a user.
     *
     * @param user The user object.
     * @return A list of recipes liked by the user.
     */
    @Override
    public Page<Recipe> getSavedRecipes(UserDetails user, PageableRequest request) {
        if(user == null){
            throw new UserNotFoundException("User not found");
        }

        return recipeRepository.findRecipesSavedByUser(user.getUsername(), request.pageable());
    }

    /**
     * Retrieves all recipes created by a specific user.
     *
     * @param user The user object.
     * @param owner The username of the user whose recipes are to be retrieved.
     * @return A list of recipes created by the user.
     */
    @Override
    public Page<Recipe> getRecipesByUser(UserDetails user, String owner, PageableRequest request) {
        String username = user == null ? null : user.getUsername();
        return recipeRepository.findByRecipeOwner(owner, username, request.pageable());
    }

    /**
     * Retrieves a recommended feed for a user using a heuristic approach.
     * The recommended feed is based on the user's liked recipes, saved recipes, and other factors.
     *
     * @param user The user object.
     * @return A list of recommended recipes for the user.
     */
    @Override
    public Page<Recipe> getRecommendedFeed(UserDetails user, FeedRequest request) {
        Specification<Recipe> likedRecipesSpec = (root, query, cb) -> {
            Join<Recipe, User> likesJoin = root.join("likes");
            return cb.equal(likesJoin.get("username"), user.getUsername());
        };

        Specification<Recipe> followedUsersRecipesSpec = (root, query, cb) -> {
            Join<Recipe, User> ownerJoin = root.join("recipeOwner");
            List<User> followedUsers = userRepository.findFollowedByUser(user.getUsername(), Pageable.unpaged()).getContent().stream().map(UserFollow::getFollowed).toList();
            return ownerJoin.in(followedUsers);
        };

        Specification<Recipe> popularRecipesSpec = (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("publishDate"), Timestamp.valueOf(LocalDateTime.now().minusDays(30)));

        Specification<Recipe> combinedSpec = Specification.where(likedRecipesSpec)
            .or(followedUsersRecipesSpec)
            .or(popularRecipesSpec);

        return recipeRepository.findAll(combinedSpec,request.pageable());
    }

    /**
     * Checks if a user likes a recipe.
     *
     * @param user The user object.
     * @return True if the user likes the recipe, false otherwise.
     */
    @Override
    public boolean userLikes(CustomUserDetails user, Recipe recipe) {
        if (user == null) {
            return false;
        }
        return recipeRepository.existsByLikesUsername(user.getUsername(), recipe);
    }

    /**
     * Checks if a user has saved a recipe.
     *
     * @param user The user object.
     * @return True if the user has saved the recipe, false otherwise.
     */
    @Override
    public boolean userSaved(CustomUserDetails user, Recipe recipe) {
        if (user == null) {
            return false;
        }
        return recipeRepository.existsByUsersWhoSavedUsername(user.getUsername(), recipe);
    }

    /**
     * Retrieves a list of users who liked a recipe.
     *
     * @param user The user object.
     * @param recipeId The ID of the recipe.
     * @return A list of users who liked the recipe.
     */
    @Override
    public Page<User> getLikesOf(CustomUserDetails user, String recipeId, PageableRequest request) {
        return userRepository.findUsersWhoLikedRecipe(UUID.fromString(recipeId), user.getUsername(), request.pageable());
    }
}
