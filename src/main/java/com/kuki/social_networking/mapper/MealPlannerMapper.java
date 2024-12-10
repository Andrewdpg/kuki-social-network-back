package com.kuki.social_networking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.kuki.social_networking.model.MealPlanner;
import com.kuki.social_networking.response.MealPlannerResponse;

/**
 * Mapper for converting meal planner entities to meal planner responses.
 */
@Mapper(componentModel = "spring")
public interface MealPlannerMapper {

    /**
     * Converts a meal planner entity to a meal planner response.
     *
     * @param mealPlanner The meal planner entity.
     * @return The meal planner response.
     */
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "isPublic", target = "isPublic")
    })
    MealPlannerResponse toMealPlannerResponse(MealPlanner mealPlanner);

}

