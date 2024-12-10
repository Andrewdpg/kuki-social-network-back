package com.kuki.social_networking.request;

import java.util.UUID;

import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private UUID parentCommentId;
}