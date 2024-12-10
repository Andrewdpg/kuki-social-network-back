package com.kuki.social_networking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.kuki.social_networking.model.Tag;
import com.kuki.social_networking.response.TagResponse;

/**
 * Mapper interface for converting Tag entities to TagResponse DTOs.
 */
@Mapper(componentModel = "spring")
public interface TagMapper {
    
        @Mappings({
            @Mapping(source = "name", target = "name")
        })
        TagResponse toTagResponse(Tag tag);

}