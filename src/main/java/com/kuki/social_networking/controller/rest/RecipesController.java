package com.kuki.social_networking.controller.rest;

import com.kuki.social_networking.exception.RecipeNotFoundException;
import com.kuki.social_networking.mapper.RecipeMapper;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.request.*;
import com.kuki.social_networking.response.RecipeResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.RecipeService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;
import com.kuki.social_networking.util.converter.StringToIngredientDTOListConverter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final StringToIngredientDTOListConverter stringToIngredientDTOListConverter;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "ingredients", new CustomCollectionEditor(List.class) {
            @Override
            protected Object convertElement(Object element) {
                if (element instanceof String) {
                    return stringToIngredientDTOListConverter.convert((String) element);
                }
                return super.convertElement(element);
            }
        });
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeResponse> createRecipe(@AuthenticatedUser CustomUserDetails user, @ModelAttribute CreateRecipeRequest request) {
        Recipe createdRecipe = recipeService.createRecipe(user, request);
        RecipeResponse recipeResponse = recipeMapper.toRecipeResponse(createdRecipe);
        recipeResponse.setLikedByUser(false);
        recipeResponse.setSavedByUser(false);
        return ResponseEntity.ok(recipeResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@AuthenticatedUser CustomUserDetails user, @PathVariable String id) {
        Recipe recipe = recipeService.getRecipeById(user, UUID.fromString(id));
        RecipeResponse recipeResponse = recipeMapper.toRecipeResponse(recipe);
        recipeResponse.setLikedByUser(recipeService.userLikes(user, recipe));
        recipeResponse.setSavedByUser(recipeService.userSaved(user, recipe));
        return ResponseEntity.ok(recipeResponse);
    }

    @GetMapping("/save")
    public ResponseEntity<Page<RecipeResponse>> getRecipesSavedByUser(@AuthenticatedUser CustomUserDetails user) {
        PageableRequest pageableRequest = new PageableRequest();
        pageableRequest.setPage(0);
        pageableRequest.setPageSize(10);

        Page<Recipe> recipes = recipeService.getSavedRecipes(user, pageableRequest);
        Page<RecipeResponse> recipeResponses = recipes.map((recipe) -> {
            RecipeResponse recipeResponse = recipeMapper.toRecipeResponse(recipe);
            recipeResponse.setLikedByUser(recipeService.userLikes(user, recipe));
            recipeResponse.setSavedByUser(recipeService.userSaved(user, recipe));
            return recipeResponse;
        });

        return ResponseEntity.ok(recipeResponses);
    }

    @PostMapping("/save/{recipeId}")
    public ResponseEntity<Void> saveRecipe(@AuthenticatedUser CustomUserDetails user, @PathVariable String recipeId) {
        recipeService.saveRecipe(user, UUID.fromString(recipeId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/save/{recipeId}")
    public ResponseEntity<Void> unsaveRecipe(@AuthenticatedUser CustomUserDetails user, @PathVariable String recipeId) {
        recipeService.unsaveRecipe(user, UUID.fromString(recipeId));
        return ResponseEntity.ok().build();
    }



    @GetMapping("")
    public ResponseEntity<Page<RecipeResponse>> getRecipes(@AuthenticatedUser CustomUserDetails user, @ModelAttribute SearchRecipesRequest request) {
        Page<Recipe> recipes = recipeService.searchRecipes(user, request);
        Page<RecipeResponse> recipeResponses = recipes.map((recipe) -> {
            RecipeResponse recipeResponse = recipeMapper.toRecipeResponse(recipe);
            recipeResponse.setLikedByUser(recipeService.userLikes(user, recipe));
            recipeResponse.setSavedByUser(recipeService.userSaved(user, recipe));
            return recipeResponse;
        });
        return ResponseEntity.ok(recipeResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@AuthenticatedUser CustomUserDetails user, @PathVariable String id) {
        recipeService.deleteRecipe(user, UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@AuthenticatedUser CustomUserDetails user, @PathVariable String id, @RequestBody UpdateRecipeRequest request) {
        Recipe updatedRecipe = recipeService.updateRecipe(user, UUID.fromString(id), request);
        RecipeResponse recipeResponse = recipeMapper.toRecipeResponse(updatedRecipe);
        recipeResponse.setLikedByUser(recipeService.userLikes(user, updatedRecipe));
        recipeResponse.setSavedByUser(recipeService.userSaved(user, updatedRecipe));
        return ResponseEntity.ok(recipeResponse);
    }

    // ! Exception Handler

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handlerRecipeNotFoundException(RecipeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}