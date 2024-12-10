package com.kuki.social_networking.controller.rest;

import com.kuki.social_networking.exception.IngredientNotFoundException;
import com.kuki.social_networking.mapper.IngredientMapper;
import com.kuki.social_networking.model.Ingredient;
import com.kuki.social_networking.request.AddIngredientRequest;
import com.kuki.social_networking.request.UpdateIngredientRequest;
import com.kuki.social_networking.response.IngredientDetailResponse;
import com.kuki.social_networking.response.IngredientSummaryResponse;
import com.kuki.social_networking.service.interfaces.IngredientService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    @PostMapping("")
    public ResponseEntity<IngredientDetailResponse> createIngredient(@RequestBody AddIngredientRequest addIngredientRequest) {
        Ingredient createdIngredient = ingredientService.createIngredient(addIngredientRequest);
        return ResponseEntity.ok(ingredientMapper.toIngredientDetailResponse(createdIngredient));
    }

    @GetMapping("")
    public ResponseEntity<Page<IngredientSummaryResponse>> getIngredients(@RequestParam int page, @RequestParam int size) {
        Page<Ingredient> ingredients = ingredientService.findAllOrderByName(PageRequest.of(page, size));
        Page<IngredientSummaryResponse> ingredientSummaryResponses = ingredients.map(ingredientMapper::toIngredientSummaryResponse);
        return ResponseEntity.ok(ingredientSummaryResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientDetailResponse> getIngredient(@PathVariable String id) {
        Ingredient ingredient = ingredientService.findIngredientById(UUID.fromString(id));
        return ResponseEntity.ok(ingredientMapper.toIngredientDetailResponse(ingredient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateIngredient(@PathVariable String id, @RequestBody UpdateIngredientRequest updateIngredientRequest) {
        Ingredient updatedIngredient = ingredientService.updateRecipe(UUID.fromString(id), updateIngredientRequest);
        return ResponseEntity.ok(ingredientMapper.toIngredientDetailResponse(updatedIngredient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable String id) {
        ingredientService.deleteIngredient(UUID.fromString(id));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<IngredientSummaryResponse>> getIngredientsByName(@PathVariable String name) {
        List<Ingredient> ingredients = ingredientService.findIngredientMatchByName(name);
        List<IngredientSummaryResponse> ingredientSummaryResponses = ingredients.stream()
            .map(ingredientMapper::toIngredientSummaryResponse)
            .toList();
        return ResponseEntity.ok(ingredientSummaryResponses);
    }

    // ! Exception Handlers

    @ExceptionHandler(IngredientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleIngredientNotFoundException(IngredientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}
