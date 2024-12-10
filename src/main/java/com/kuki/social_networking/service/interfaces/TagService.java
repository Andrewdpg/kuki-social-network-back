package com.kuki.social_networking.service.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.request.TagRequest;

/**
 * Interface for handling tag-related operations.
 */
public interface TagService {

    /** Retrieves all tags.
    *
    * @return A list of all tags.
    */
    List<Tag> getAllTags();

    /**
    * Retrieves a tag by its name.
    *
    * @param name The tag's name.
    * @return The tag with the specified name.
    */
    Tag getTagByName(String name);

    /**
    * Adds a tag to the database.
    *
    * @param name The tag's name.
    * @return The added tag.
    */
    Tag addTag(String name);

    /**
    * Deletes a tag from the database.
    *
    * @param name The tag's name.
    */
    void deleteTag(String name);

    /**
     * Filters tags based on the criteria specified in the TagRequest.
     *
     * @param tagRequest the request object containing the filtering criteria
     * @return a paginated list of tags that match the filtering criteria
     */
    Page<Tag> filterTags(TagRequest tagRequest);

    /**
     * Retrieves the top tags based on their usage in recipes.
     *
     * @param pageable the pagination information
     * @return a list of top tags with their usage count
     */
    Page<Object[]> getTopTags(Pageable pageable);
}