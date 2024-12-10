package com.kuki.social_networking.request;

import lombok.Data;

import java.util.UUID;

@Data
public class SearchRecipeStepsRequest extends PageableRequest{
        private UUID recipeId;

}
