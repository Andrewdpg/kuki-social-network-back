package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.Comment;
import com.kuki.social_networking.request.CommentRequest;

import com.kuki.social_networking.request.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface CommentService {
    Comment addComment(UserDetails user, UUID recipeId, CommentRequest commentRequest);
    Comment editComment(UserDetails user, UUID commentId, String comment);
    void deleteComment(UserDetails user, UUID commentId);
    Page<Comment> showRecipeComments(UUID recipeId, PageableRequest pageableRequest);
    Page<Comment> showSubComments(UUID recipeId, PageableRequest pageableRequest);
}
