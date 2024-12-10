package com.kuki.social_networking.repository;

import com.kuki.social_networking.model.Comment;
import java.util.List;
import java.util.UUID;

import com.kuki.social_networking.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * Repository for managing {@link Comment} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for complex queries.
 */
public interface CommentRepository extends JpaRepository<Comment, UUID>, JpaSpecificationExecutor<Comment> {

    /**
     * Finds the most recent comments for a given recipe, ordered by creation date.
     *
     * @param recipeId the ID of the recipe.
     * @param pageable the pageable object for pagination.
     * @return a page of comments for the recipe.
     */
    @Query("SELECT c FROM Comment c WHERE c.recipe.id = :recipeId AND c.parentComment IS NULL")
    Page<Comment> findParentsByRecipe_Id(UUID recipeId, Pageable pageable);

    /**
     * Finds all comments created by a given user.
     *
     * @param user the user who created the comments.
     * @return a list of comments created by the user.
     */
    List<Comment> findAllByCommentOwner(User user);

    /**
     * Finds all comments created by a given user, ordered by creation date.
     *
     * @param recipeId the ID of the recipe.
     * @param parentCommentId the ID of the parent comment.
     * @param pageable the pageable object for pagination.
     * @return a page of comments responses for the parent comment.
     */
    Page<Comment> findAllByRecipe_IdAndParentComment_Id(UUID recipeId, UUID parentCommentId, Pageable pageable);

}
