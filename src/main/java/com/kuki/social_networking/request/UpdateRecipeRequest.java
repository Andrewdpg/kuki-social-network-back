package com.kuki.social_networking.request;

import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.RecipeDifficulty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Request object for updating a recipe.
 */
@Data
@Builder
public class UpdateRecipeRequest {

    private String title;
    private String description;
    private String photoUrl;
    private String country;
    private RecipeDifficulty difficulty;

}
