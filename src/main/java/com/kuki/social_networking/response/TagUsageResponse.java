package com.kuki.social_networking.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response DTO for tag usage information.
 */
@Data
@AllArgsConstructor
public class TagUsageResponse {
    private String tagName;
    private Long usageCount;
    private String country;
}