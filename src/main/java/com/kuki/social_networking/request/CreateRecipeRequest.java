package com.kuki.social_networking.request;

import com.kuki.social_networking.model.RecipeDifficulty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


/**
 * Request object for creating a new recipe.
 */
@Data
@Builder
public class CreateRecipeRequest {

    String title;
    String description;
    MultipartFile image;
    RecipeDifficulty difficulty;
    String country;
    List<IngredientDTO> ingredients;
    List<String> tags;
}

