package com.kuki.social_networking.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateMealDayRequest {

    int day;
    String description;
    List<UUID> recipeIds;

}
