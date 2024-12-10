package com.kuki.social_networking.controller.rest;

import com.kuki.social_networking.mapper.UserMapper;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.response.DeleteResponse;
import com.kuki.social_networking.response.LikeResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.NotificationService;
import com.kuki.social_networking.service.interfaces.RecipeService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/recipes/{recipeId}/like")
@RequiredArgsConstructor
public class LikeController {

    private final UserMapper userMapper;
    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<LikeResponse> likeRecipe(@AuthenticatedUser CustomUserDetails user, @PathVariable String recipeId) {
        User liker = recipeService.likeRecipe(user, UUID.fromString(recipeId));
        return ResponseEntity.ok(LikeResponse.builder()
                .recipeId(UUID.fromString(recipeId))
                .user(userMapper.toSimpleUserResponse(liker))
                .liked(true)
                .build());
    }

    @DeleteMapping
    public ResponseEntity<DeleteResponse> unlikeRecipe(@AuthenticatedUser CustomUserDetails user, @PathVariable String recipeId) {
        recipeService.unlikeRecipe(user, UUID.fromString(recipeId));
        return ResponseEntity.ok(DeleteResponse.builder()
                .message("Recipe unliked successfully")
                .deleted(true)
                .build());
    }

    @GetMapping
    public ResponseEntity<Page<LikeResponse>> getLikeStatus(@AuthenticatedUser CustomUserDetails user, @PathVariable String recipeId, @RequestBody PageableRequest request) {
        Page<User> users = recipeService.getLikesOf(user, recipeId, request);
        return ResponseEntity.ok(users.map(u -> LikeResponse.builder()
                .recipeId(UUID.fromString(recipeId))
                .user(userMapper.toSimpleUserResponse(u))
                .liked(true)
                .build()));
    }

}
