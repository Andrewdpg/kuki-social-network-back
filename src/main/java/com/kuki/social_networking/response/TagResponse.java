package com.kuki.social_networking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for tag information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagResponse {
    private String name;
}