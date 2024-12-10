package com.kuki.social_networking.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Request object for fetching the feed.
 */
@Data
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FeedRequest extends PageableRequest {
    private String sortBy = "publishDate";
    private String direction = "desc";
}
