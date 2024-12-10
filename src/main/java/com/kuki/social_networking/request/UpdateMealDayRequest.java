package com.kuki.social_networking.request;

import com.kuki.social_networking.util.crud.Operation;
import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

@Data
public class UpdateMealDayRequest {
    Integer newDay;
    String description;
    HashMap<UUID, Operation> recipeIds;
}
