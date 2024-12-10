package com.kuki.social_networking.service.implementation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.repository.TagRepository;
import com.kuki.social_networking.request.TagRequest;
import com.kuki.social_networking.service.interfaces.TagService;
import static com.kuki.social_networking.util.EntitySpecs.getPageable;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the tag service.
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    /**
     * Gets all tags.
     *
     * @return A list of all tags.
     */
    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    } 

    /**
     * Filters tags based on the criteria specified in the TagRequest.
     *
     * @param tagRequest The request object containing the filtering criteria.
     * @return A paginated list of tags that match the filtering criteria.
     */
    @Override
    public Page<Tag> filterTags(TagRequest tagRequest) {
        return tagRepository.searchTags(tagRequest, getPageable(tagRequest));
    }

    /**
     * Gets a tag by its name.
     *
     * @param name The tag's name.
     * @return The tag.
     */
    @Override
    public Tag getTagByName(String name) {

        String validatedName = validateTag(name);

        return tagRepository.findById(name)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found: " + validatedName + "."));
    }

    /**
     * Adds a tag to the database.
     *
     * @param name The tag's name.
     * @return The added tag.
     */
    @Override
    public Tag addTag(String name) {

        String validatedName = validateTag(name);

        tagRepository.findById(validatedName).ifPresent(tag -> {
            throw new IllegalArgumentException("Tag already exists: " + validatedName + ".");
        });

        Tag tagToAdd = Tag.builder()
                .name(validatedName)
                .build();

        tagRepository.save(tagToAdd);

        return tagToAdd;
    }

    /**
     * Deletes a tag from the database.
     *
     * @param name The tag's name.
     */
    @Override
    public void deleteTag(String name) {

        String validatedName = validateTag(name);

        tagRepository.findById(validatedName).ifPresentOrElse(
                tagRepository::delete,
                () -> {
                    throw new IllegalArgumentException("Tag not found: " + validatedName + ".");
                });
    }

    /**
     * Validates a tag name.
     *
     * @param name The tag's name to validate.
     * @return The validated tag name.
     */
    private String validateTag (String name) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name cannot be empty.");
        }

        return name.toLowerCase();
    }

    /**
     * Gets the top tags.
     *
     * @param pageable The pagination information.
     * @return A paginated list of the top tags.
     */
    @Override
    public Page<Object[]> getTopTags(Pageable pageable) {
        return tagRepository.findTopTags(pageable);
    }

}
