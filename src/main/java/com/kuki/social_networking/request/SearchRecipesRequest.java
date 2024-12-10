package com.kuki.social_networking.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

/**
 * Request object for searching recipes.
 */
@Data
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SearchRecipesRequest extends PageableRequest {
    private String sortBy = "title";
    private Duration estimatedTime;
    private String country;
    private String difficulty;
    private String title;
    private List<UUID> ingredients;
    private List<String> tags;
}
