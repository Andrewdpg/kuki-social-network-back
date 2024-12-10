package com.kuki.social_networking.request;

import com.kuki.social_networking.model.IngredientUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class AddIngredientRequest {

    @NotBlank(message = "Ingredient name is mandatory")
    private String name;

    @NotBlank(message = "Ingredient photo is mandatory")
    private String photoUrl;

    @NotNull(message = "Nutritional information is mandatory")
    private Map<String, Object> nutritionalInfo;

    @NotNull(message = "Ingredient unit is mandatory")
    private IngredientUnit unit;
}