package com.kuki.social_networking.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for following a user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowRequest {
    private String followedUsername;
}