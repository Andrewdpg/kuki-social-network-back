package com.kuki.social_networking.request;

import com.kuki.social_networking.model.Ingredient;
import com.kuki.social_networking.model.IngredientUnit;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class UpdateIngredientRequest {

    private String name;
    private String photoURL;
    private Map<String, Object> nutritionalInfo;
    private IngredientUnit unit;
}
