package com.kuki.social_networking.response;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MealDayResponse {

    int day;
    String description;
    UUID mealPlannerId;
    List<RecipeResponse> recipes;

}
