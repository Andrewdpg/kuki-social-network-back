package com.kuki.social_networking.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LikeResponse {
    private UUID recipeId;
    private SimpleUserResponse user;
    private boolean liked;
}
