package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Comment;
import com.kuki.social_networking.response.CommentResponse;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public CommentResponse toCommentResponse(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponse commentResponse = new CommentResponse();

        commentResponse.setCommentId( comment.getId() );
        commentResponse.setCreationDate( comment.getCreationDate() );
        commentResponse.setContent( comment.getContent() );
        commentResponse.setUser( userMapper.toSimpleUserResponse( comment.getCommentOwner() ) );

        return commentResponse;
    }
}
