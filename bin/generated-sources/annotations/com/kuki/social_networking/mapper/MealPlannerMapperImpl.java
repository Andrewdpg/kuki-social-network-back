package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.MealPlanner;
import com.kuki.social_networking.response.MealPlannerResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class MealPlannerMapperImpl implements MealPlannerMapper {

    @Override
    public MealPlannerResponse toMealPlannerResponse(MealPlanner mealPlanner) {
        if ( mealPlanner == null ) {
            return null;
        }

        MealPlannerResponse mealPlannerResponse = new MealPlannerResponse();

        mealPlannerResponse.setId( mealPlanner.getId() );
        mealPlannerResponse.setName( mealPlanner.getName() );
        mealPlannerResponse.setDescription( mealPlanner.getDescription() );
        mealPlannerResponse.setIsPublic( mealPlanner.getIsPublic() );

        return mealPlannerResponse;
    }
}
