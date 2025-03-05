package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.MealDay;
import com.kuki.social_networking.model.MealDayId;
import com.kuki.social_networking.model.MealPlanner;
import com.kuki.social_networking.response.MealDayResponse;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class MealDayMapperImpl implements MealDayMapper {

    @Override
    public MealDayResponse toMealDayResponse(MealDay mealDay) {
        if ( mealDay == null ) {
            return null;
        }

        MealDayResponse mealDayResponse = new MealDayResponse();

        mealDayResponse.setDay( mealDayIdDay( mealDay ) );
        mealDayResponse.setDescription( mealDay.getDescription() );
        mealDayResponse.setMealPlannerId( mealDayMealPlannerId( mealDay ) );
        mealDayResponse.setRecipes( mapRecipes( mealDay.getRecipes() ) );

        return mealDayResponse;
    }

    private int mealDayIdDay(MealDay mealDay) {
        if ( mealDay == null ) {
            return 0;
        }
        MealDayId id = mealDay.getId();
        if ( id == null ) {
            return 0;
        }
        int day = id.getDay();
        return day;
    }

    private UUID mealDayMealPlannerId(MealDay mealDay) {
        if ( mealDay == null ) {
            return null;
        }
        MealPlanner mealPlanner = mealDay.getMealPlanner();
        if ( mealPlanner == null ) {
            return null;
        }
        UUID id = mealPlanner.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
