package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Country;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.response.SimpleUserResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:05-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public SimpleUserResponse toSimpleUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        SimpleUserResponse.SimpleUserResponseBuilder simpleUserResponse = SimpleUserResponse.builder();

        simpleUserResponse.username( user.getUsername() );
        simpleUserResponse.photoUrl( user.getPhotoUrl() );
        simpleUserResponse.fullName( user.getFullName() );
        simpleUserResponse.biography( user.getBiography() );
        Map<String, Object> map = user.getSocialMedia();
        if ( map != null ) {
            simpleUserResponse.socialMedia( new LinkedHashMap<String, Object>( map ) );
        }
        simpleUserResponse.email( user.getEmail() );
        simpleUserResponse.country( userCountryName( user ) );

        return simpleUserResponse.build();
    }

    private String userCountryName(User user) {
        if ( user == null ) {
            return null;
        }
        Country country = user.getCountry();
        if ( country == null ) {
            return null;
        }
        String name = country.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
