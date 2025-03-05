package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Ingredient;
import com.kuki.social_networking.response.IngredientDetailResponse;
import com.kuki.social_networking.response.IngredientSummaryResponse;
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
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientSummaryResponse toIngredientSummaryResponse(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientSummaryResponse ingredientSummaryResponse = new IngredientSummaryResponse();

        if ( ingredient.getId() != null ) {
            ingredientSummaryResponse.setId( ingredient.getId().toString() );
        }
        ingredientSummaryResponse.setName( ingredient.getName() );
        ingredientSummaryResponse.setPhotoUrl( ingredient.getPhotoUrl() );
        if ( ingredient.getUnit() != null ) {
            ingredientSummaryResponse.setUnit( ingredient.getUnit().name() );
        }

        return ingredientSummaryResponse;
    }

    @Override
    public IngredientDetailResponse toIngredientDetailResponse(Ingredient ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDetailResponse ingredientDetailResponse = new IngredientDetailResponse();

        if ( ingredient.getId() != null ) {
            ingredientDetailResponse.setId( ingredient.getId().toString() );
        }
        ingredientDetailResponse.setName( ingredient.getName() );
        ingredientDetailResponse.setPhotoUrl( ingredient.getPhotoUrl() );
        Map<String, Object> map = ingredient.getNutritionalInfo();
        if ( map != null ) {
            ingredientDetailResponse.setNutritionalInfo( new LinkedHashMap<String, Object>( map ) );
        }
        if ( ingredient.getUnit() != null ) {
            ingredientDetailResponse.setUnit( ingredient.getUnit().name() );
        }

        return ingredientDetailResponse;
    }
}
