package com.kuki.social_networking.controller.rest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kuki.social_networking.mapper.TagMapper;
import com.kuki.social_networking.request.TagRequest;
import com.kuki.social_networking.response.TagResponse;
import com.kuki.social_networking.response.TagUsageResponse;
import com.kuki.social_networking.service.interfaces.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    /**
     * Retrieves a paginated list of tags filtered by the provided name.
     *
     * @param name     the name to filter tags by, optional
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of TagResponse objects
     */
    @GetMapping
    public ResponseEntity<Page<TagResponse>> getTags(
            @RequestParam(name = "name", required = false) String name,
            Pageable pageable) {
        TagRequest tagRequest = TagRequest.builder().name(name).build();
        Page<TagResponse> tags = tagService.filterTags(tagRequest).map(tagMapper::toTagResponse);
        return ResponseEntity.ok(tags);
    }

    /**
     * Retrieves the top tags based on their usage in recipes.
     *
     * @param pageable the pagination information
     * @return a ResponseEntity containing a Page of top tags with their usage count
     */
    @GetMapping("/trending")
    public ResponseEntity<Page<TagUsageResponse>> getTrendingTags(Pageable pageable) {
        Page<Object[]> trendingTags = tagService.getTopTags(pageable);
        Page<TagUsageResponse> response = trendingTags.map(objects -> new TagUsageResponse((String) objects[0], (Long) objects[1], (String) objects[2]));
        return ResponseEntity.ok(response);
    }

    // Exception Handlers
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}