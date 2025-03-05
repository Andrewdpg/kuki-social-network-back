package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.response.CountryResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public CountryResponse toCountryResponse(Country country) {
        if ( country == null ) {
            return null;
        }

        CountryResponse countryResponse = new CountryResponse();

        countryResponse.setCode( country.getCode() );
        countryResponse.setName( country.getName() );

        return countryResponse;
    }
}
