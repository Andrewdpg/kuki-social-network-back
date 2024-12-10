package com.kuki.social_networking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.request.TagRequest;

/**
 * Repository for managing {@link Tag} entities.
 * Extends {@link JpaRepository} for basic CRUD operations.
 */
public interface TagRepository extends JpaRepository<Tag, String> {

    /**
     * Finds tags associated with a specific recipe by the recipe's ID.
     *
     * @param recipeId the ID of the recipe.
     * @return a list of tags associated with the specified recipe.
     */
    List<Tag> findByRecipes_Id(UUID recipeId);

    
    /**
     * Searches for tags based on the provided TagRequest.
     * 
     * @param tagRequest the request object containing the search criteria for tags.
     *                   If the name in the tagRequest is null, all tags will be returned.
     * @param pageable   the pagination information.
     * @return a page of tags that match the search criteria.
     */
    @Query("SELECT t FROM Tag t WHERE (:#{#tagRequest.name} IS NULL OR t.name = :#{#tagRequest.name})")   
    Page<Tag> searchTags(@Param("tagRequest") TagRequest tagRequest, Pageable pageable);

    /**
     * Finds the top tags based on the number of times they are used in recipes.
     * 
     * @param pageable the pagination information.
     * @return a page of the top tags.
     */
    @Query("SELECT t.name, COUNT(r) as usageCount, r.country.name FROM Tag t JOIN t.recipes r GROUP BY t.name, r.country.name ORDER BY usageCount DESC")
    Page<Object[]> findTopTags(Pageable pageable);

    /**
     * Finds tags by their names.
     *
     * @param tagNames the names of the tags.
     * @return a list of tags with the specified names.
     */
    @Query("SELECT t FROM Tag t WHERE t.name IN :tagNames")
    List<Tag> findByNameIn(List<String> tagNames);

    Tag findByNameIgnoreCase(String name);
}

