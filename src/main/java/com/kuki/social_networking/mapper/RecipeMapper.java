package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.response.CountryResponse;
import com.kuki.social_networking.response.RecipeResponse;
import com.kuki.social_networking.response.SimpleUserResponse;
import com.kuki.social_networking.response.TagResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    TagMapper tagMapper = Mappers.getMapper(TagMapper.class);
    CountryMapper countryMapper = Mappers.getMapper(CountryMapper.class);
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "id",target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "photoUrl", target = "photoUrl"),
            @Mapping(source = "publishDate", target = "publishDate"),
            @Mapping(source = "estimatedTime", target = "estimatedTime"),
            @Mapping(source = "country", target = "country"),
            @Mapping(source = "difficulty", target = "difficulty"),
            @Mapping(source = "tags", target = "tags"),
            @Mapping(source = "likesCount", target = "likes"),
            @Mapping(source = "commentsCount", target = "comments"),
    })
    RecipeResponse toRecipeResponse(Recipe recipe);

    /**
     * Converts a list of Tag entities to a list of TagResponses.
     *
     * @param tags The list of Tag entities.
     * @return The list of TagResponses.
     */
    default List<TagResponse> mapTags(List<Tag> tags) {
        return tags.stream().map(tagMapper::toTagResponse).collect(Collectors.toList());
    }

    /**
     * Converts a Country entity to a CountryResponse.
     *
     * @param country The Country entity.
     * @return The CountryResponse.
     */
    default CountryResponse mapCountry(Country country) {
        return countryMapper.toCountryResponse(country);
    }

    /**
     * Converts a User entity to a SimpleUserResponse.
     *
     * @param user The User entity.
     * @return The SimpleUserResponse.
     */
    default SimpleUserResponse mapUser(User user) {
        return userMapper.toSimpleUserResponse(user);
    }
}
