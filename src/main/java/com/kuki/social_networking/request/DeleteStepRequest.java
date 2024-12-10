package com.kuki.social_networking.request;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteStepRequest {
    private UUID stepId;
    private UUID recipeId;
}
