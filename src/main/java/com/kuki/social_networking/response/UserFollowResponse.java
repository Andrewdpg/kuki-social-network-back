package com.kuki.social_networking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

/**
 * Response DTO for user follow information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFollowResponse {
    private String followerUsername;
    private String followedUsername;
    private Timestamp creationDate;
}

