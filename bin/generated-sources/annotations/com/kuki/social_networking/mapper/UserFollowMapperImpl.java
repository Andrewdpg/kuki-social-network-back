package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.model.UserFollow;
import com.kuki.social_networking.response.UserFollowResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:05-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserFollowMapperImpl implements UserFollowMapper {

    @Override
    public UserFollowResponse toUserFollowResponse(UserFollow userFollow) {
        if ( userFollow == null ) {
            return null;
        }

        UserFollowResponse.UserFollowResponseBuilder userFollowResponse = UserFollowResponse.builder();

        userFollowResponse.followerUsername( userFollowFollowerUsername( userFollow ) );
        userFollowResponse.followedUsername( userFollowFollowedUsername( userFollow ) );
        userFollowResponse.creationDate( map( userFollow.getCreationDate() ) );

        return userFollowResponse.build();
    }

    private String userFollowFollowerUsername(UserFollow userFollow) {
        if ( userFollow == null ) {
            return null;
        }
        User follower = userFollow.getFollower();
        if ( follower == null ) {
            return null;
        }
        String username = follower.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String userFollowFollowedUsername(UserFollow userFollow) {
        if ( userFollow == null ) {
            return null;
        }
        User followed = userFollow.getFollowed();
        if ( followed == null ) {
            return null;
        }
        String username = followed.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }
}
