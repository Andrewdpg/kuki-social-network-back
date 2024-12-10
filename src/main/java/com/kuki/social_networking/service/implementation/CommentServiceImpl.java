package com.kuki.social_networking.service.implementation;


import com.kuki.social_networking.exception.CommentNotFoundException;
import com.kuki.social_networking.exception.RecipeNotFoundException;
import com.kuki.social_networking.exception.UserNotFoundException;
import com.kuki.social_networking.model.Comment;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.repository.CommentRepository;
import com.kuki.social_networking.repository.RecipeRepository;
import com.kuki.social_networking.repository.UserRepository;
import com.kuki.social_networking.request.CommentRequest;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.service.interfaces.CommentService;

import com.kuki.social_networking.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final NotificationService notificationService;

    /**
     * Adds a comment to a recipe.
     *
     * @param user the user adding the comment.
     * @param commentRequest the request containing the comment data.
     */
    @Override
    public Comment addComment(UserDetails user, UUID recipeId, CommentRequest commentRequest) {

        if(user == null) {
            throw new IllegalArgumentException("User must be authenticated");
        }

        User owner = validateUser(user.getUsername());

        Recipe recipe = recipeRepository.findAccessibleRecipeById(recipeId, user.getUsername())
            .orElseThrow(() -> new RecipeNotFoundException("Recipe not found."));

        Comment parentComment = null;
        if (commentRequest.getParentCommentId() != null) {
            parentComment = checkComment(commentRequest.getParentCommentId());
            if (!parentComment.getRecipe().getId().equals(recipe.getId())) {
                throw new IllegalArgumentException("Parent comment does not belong to the recipe.");
            }
        }
        
        validateComment(commentRequest.getContent());

        Comment commentToAdd = Comment.builder()
                .content(commentRequest.getContent())
                .creationDate(Timestamp.from(Instant.now()))
                .recipe(recipe)
                .commentOwner(owner)
                .parentComment(parentComment)
                .build();

        String notificationContent;

        if (parentComment == null) {
            notificationContent = "You have a new comment on your recipe \"" + recipe.getTitle() + "\"";
        } else {
            notificationContent = "You have a new answer in your comment by \"" + user.getUsername() + "\"";
        }

        notificationService.sendNotification(
                recipe.getRecipeOwner().getUsername(),
                notificationContent,
                "url"
        );

        return commentRepository.save(commentToAdd);
    }

    /**
     * Edits a comment.
     *
     * @param commentId the ID of the comment to edit.
     * @param comment the new content of the comment.
     */
    @Override
    public Comment editComment(UserDetails user, UUID commentId, String comment) {

        if(user == null) {
            throw new IllegalArgumentException("User must be authenticated");
        }

        validateComment(comment);
        Comment commentToEdit = checkComment(commentId);
        
        if (!commentToEdit.getCommentOwner().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("User is not the owner of the comment.");
        }

        commentToEdit.setContent(comment);
        commentToEdit.setCreationDate(Timestamp.from(Instant.now()));
        return commentRepository.save(commentToEdit);
    }

    /**
     * Deletes a comment.
     *
     * @param commentId the ID of the comment to delete.
     */
    @Override
    public void deleteComment(UserDetails user, UUID commentId) {
        if(user == null) {
            throw new IllegalArgumentException("User must be authenticated");
        }

        Comment commentToDelete = checkComment(commentId);

        if (!commentToDelete.getCommentOwner().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("User is not the owner of the comment.");
        }

        commentRepository.delete(commentToDelete);
    }

    /**
     * Shows the comments of a recipe.
     *
     * @param recipeId the ID of the recipe to show the comments of.
     */
    @Override
    public Page<Comment> showRecipeComments(UUID recipeId, PageableRequest request) {
        request.setSortBy("creationDate");
        recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found."));
        return commentRepository.findParentsByRecipe_Id(recipeId, request.pageable());
    }

    /**
     * Shows the responses of a comment.
     *
     * @param commentId the ID of the comment to show the responses of.
     */
    @Override
    public Page<Comment> showSubComments(UUID commentId, PageableRequest request) {
        request.setSortBy("creationDate");
        Comment parentComment = checkComment(commentId);
        return commentRepository.findAllByRecipe_IdAndParentComment_Id(
                parentComment.getRecipe().getId(),
                parentComment.getId(),
                request.pageable()
        );
    }

    private User validateUser(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    private Comment checkComment(UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found."));
    }

    private void validateComment(String comment) {
        if (comment.isEmpty() || comment.trim().isEmpty()){
            throw new IllegalArgumentException("Tag name cannot be empty.");
        }
    }
}
