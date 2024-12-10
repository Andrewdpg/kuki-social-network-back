package com.kuki.social_networking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.kuki.social_networking.model.Comment;
import com.kuki.social_networking.response.CommentResponse;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface CommentMapper {

    @Mappings({
        @Mapping(source = "id", target = "commentId"),
        @Mapping(source = "creationDate", target = "creationDate"),
        @Mapping(source = "content", target = "content"),
        @Mapping(source = "commentOwner", target = "user")
    })
    CommentResponse toCommentResponse(Comment comment);
}