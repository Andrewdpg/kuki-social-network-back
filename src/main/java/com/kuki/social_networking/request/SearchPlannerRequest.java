package com.kuki.social_networking.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchPlannerRequest extends PageableRequest {
        String name;
        UUID recipeId;
}
