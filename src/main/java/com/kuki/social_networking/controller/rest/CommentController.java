package com.kuki.social_networking.controller.rest;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kuki.social_networking.mapper.CommentMapper;
import com.kuki.social_networking.model.Comment;
import com.kuki.social_networking.request.CommentRequest;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.response.CommentResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.CommentService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    /**
     * Adds a comment to a specific recipe.
     *
     * @param user the authenticated user details
     * @param recipeId the UUID of the recipe to which the comment is being added
     * @param commentRequest the request body containing the comment details
     * @return a ResponseEntity containing the CommentResponse
     */
    @PostMapping("/recipe/{recipeId}")
    public ResponseEntity<CommentResponse> addComment(@AuthenticatedUser CustomUserDetails user, @PathVariable UUID recipeId, @RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.addComment(user, recipeId, commentRequest);

        return ResponseEntity.ok(commentMapper.toCommentResponse(comment));
    }

    /**
     * Retrieves comments for a specific recipe.
     *
     * @param recipeId the ID of the recipe for which comments are to be retrieved
     * @param request the pagination and sorting information
     * @return a ResponseEntity containing a page of CommentResponse objects
     */
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<Page<CommentResponse>> showRecipeComments(@PathVariable String recipeId, @ModelAttribute PageableRequest request) {
        Page<Comment> comments = commentService.showRecipeComments(UUID.fromString(recipeId), request);
        return ResponseEntity.ok(comments.map(commentMapper::toCommentResponse));
    }

    /**
     * Deletes a comment identified by its ID.
     *
     * @param user the authenticated user details
     * @param id the UUID of the comment to be deleted
     * @return a ResponseEntity with no content if the deletion is successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@AuthenticatedUser CustomUserDetails user, @PathVariable UUID id) {
        commentService.deleteComment(user, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Edits an existing comment.
     *
     * @param user the authenticated user details
     * @param id the UUID of the comment to be edited
     * @param commentRequest the request body containing the new content of the comment
     * @return a ResponseEntity containing the updated comment response
     */
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> editComment(@AuthenticatedUser CustomUserDetails user, @PathVariable UUID id, @RequestBody CommentRequest commentRequest) {
        Comment comment = commentService.editComment(user, id, commentRequest.getContent());
        return ResponseEntity.ok(commentMapper.toCommentResponse(comment));
    }

    /**
     * Retrieves a paginated list of sub-comments for a specific comment.
     *
     * @param id the UUID of the comment for which sub-comments are to be retrieved
     * @param request the pagination and sorting information
     * @return a ResponseEntity containing a Page of CommentResponse objects
     */
    @GetMapping("/{id}")
    public ResponseEntity<Page<CommentResponse>> showRecipeSubComments(@PathVariable String id, @ModelAttribute PageableRequest request) {
        Page<Comment> comments = commentService.showSubComments(UUID.fromString(id), request);
        return ResponseEntity.ok(comments.map(commentMapper::toCommentResponse));
    }
}
