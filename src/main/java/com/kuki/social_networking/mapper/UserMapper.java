package com.kuki.social_networking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.response.SimpleUserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
        @Mapping(source = "username", target = "username"),
        @Mapping(source = "photoUrl", target = "photoUrl"),
        @Mapping(source = "fullName", target = "fullName"),
        @Mapping(source = "biography", target = "biography"),
        @Mapping(source = "socialMedia", target = "socialMedia"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "country.name", target = "country")
    })
    SimpleUserResponse toSimpleUserResponse(User user);

    default String mapCountryToString(Country country) {
        return country != null ? country.getName() : null;
    }
}