package com.kuki.social_networking.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Setter
@Getter
public class MealPlannerResponse {

    private UUID id;
    private String name;
    private String description;
    private Boolean isPublic;

}
