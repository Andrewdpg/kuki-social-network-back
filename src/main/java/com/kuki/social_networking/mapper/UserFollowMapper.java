package com.kuki.social_networking.mapper;

import java.sql.Timestamp;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.kuki.social_networking.model.UserFollow;
import com.kuki.social_networking.response.UserFollowResponse;

/**
 * Mapper interface for converting UserFollow entities to UserFollowResponse DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserFollowMapper {

    /**
     * Maps a UserFollow entity to a UserFollowResponse DTO.
     *
     * @param userFollow The UserFollow entity to be mapped.
     * @return The mapped UserFollowResponse DTO.
     */
    @Mappings({
            @Mapping(source = "follower.username", target = "followerUsername"),
            @Mapping(source = "followed.username", target = "followedUsername"),
            @Mapping(source = "creationDate", target = "creationDate")
    })
    UserFollowResponse toUserFollowResponse(UserFollow userFollow);

    /**
     * Maps a Timestamp value.
     *
     * @param value The Timestamp value to be mapped.
     * @return The same Timestamp value.
     */
    default Timestamp map(Timestamp value) {
        return value;
    }
}