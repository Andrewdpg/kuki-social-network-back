package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.exception.IngredientNotFoundException;
import com.kuki.social_networking.model.Ingredient;
import com.kuki.social_networking.repository.IngredientRepository;
import com.kuki.social_networking.request.AddIngredientRequest;
import com.kuki.social_networking.request.UpdateIngredientRequest;
import com.kuki.social_networking.service.interfaces.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    public final IngredientRepository ingredientRepository;

    @Override
    public Ingredient createIngredient(AddIngredientRequest ingredient) {
        Ingredient newIngredient = Ingredient.builder()
                .name(ingredient.getName())
                .photoUrl(ingredient.getPhotoUrl())
                .nutritionalInfo(ingredient.getNutritionalInfo())
                .unit(ingredient.getUnit())
                .build();

        return  ingredientRepository.save(newIngredient);
    }

    @Override
    public void deleteIngredient(UUID id) {
        Ingredient ingredient = findIngredientById(id);
        ingredientRepository.delete(ingredient);
    }

    @Override
    public List<Ingredient> findIngredientMatchByName(String name) {
        return ingredientRepository.findTop10ByNameContainingIgnoreCase(name);
    }

    @Override
    public Ingredient updateRecipe(UUID id, UpdateIngredientRequest request) {
        Ingredient ingredient = findIngredientById(id);

        if (request.getName() != null)
            ingredient.setName(request.getName());

        if (request.getPhotoURL() != null)
            ingredient.setPhotoUrl(request.getPhotoURL());

        if (request.getUnit() != null)
            ingredient.setUnit(request.getUnit());

        if (request.getNutritionalInfo() != null)
            ingredient.setNutritionalInfo(request.getNutritionalInfo());

        ingredientRepository.save(ingredient);

    System.out.println(request.getName());
    System.out.println(request.getPhotoURL());
    System.out.println(request.getUnit());
    System.out.println(request.getNutritionalInfo());

        return ingredient;
    }

    @Override
    public Page<Ingredient> findAllOrderByName(Pageable pageable) {
        return ingredientRepository.findAllByOrderByNameAsc(pageable);
    }

    @Override
    public Ingredient findIngredientById(UUID id) {
        return ingredientRepository.findById(id)
            .orElseThrow(() -> new IngredientNotFoundException("Ingredient with id " + id + " not found"));
    }
}
