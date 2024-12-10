package com.kuki.social_networking.response;

import com.kuki.social_networking.model.RecipeDifficulty;
import lombok.Data;


import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Data
public class RecipeResponse {

    private UUID id;
    private String title;
    private String description;
    private String photoUrl;
    private Timestamp publishDate;
    private Duration estimatedTime;
    private CountryResponse country;
    private RecipeDifficulty difficulty;
    private SimpleUserResponse recipeOwner;
    private List<TagResponse> tags;
    private int likes;
    private int comments;
    private boolean likedByUser;
    private boolean savedByUser;

}
