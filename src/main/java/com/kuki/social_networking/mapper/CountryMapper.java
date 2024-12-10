package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.response.CountryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mappings(
            {@Mapping(source = "code", target = "code"),
            @Mapping(source = "name", target = "name")}
    )
    CountryResponse toCountryResponse(Country country);
}
