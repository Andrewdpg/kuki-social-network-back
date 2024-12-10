package com.kuki.social_networking.response;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Data;

@Data
public class CommentResponse {
    UUID commentId;
    Timestamp creationDate;
    String content;
    SimpleUserResponse user;
}
