package com.kuki.social_networking.response;

import lombok.Data;

import java.util.Map;

@Data
public class IngredientDetailResponse {

    private String id;
    private String name;
    private String photoUrl;
    private Map<String, Object> nutritionalInfo;
    private String unit;
}
