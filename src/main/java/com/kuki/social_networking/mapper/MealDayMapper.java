package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.MealDay;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.response.MealDayResponse;
import com.kuki.social_networking.response.RecipeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting meal day entities to meal day responses.
 */
@Mapper(componentModel = "spring")
public interface MealDayMapper {

    RecipeMapper recipeMapper = Mappers.getMapper(RecipeMapper.class);

    /**
     * Converts a MealDay entity to a MealDayResponse.
     *
     * @param mealDay The MealDay entity.
     * @return The MealDayResponse.
     */
    @Mappings({
        @Mapping(source = "id.day", target = "day"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "mealPlanner.id", target = "mealPlannerId"),
        @Mapping(source = "recipes", target = "recipes")
    })
    MealDayResponse toMealDayResponse(MealDay mealDay);

    /**
     * Converts a list of Recipe entities to a list of RecipeResponses.
     *
     * @param recipes The list of Recipe entities.
     * @return The list of RecipeResponses.
     */
    default List<RecipeResponse> mapRecipes(List<Recipe> recipes) {
        return recipes.stream().map(recipeMapper::toRecipeResponse).collect(Collectors.toList());
    }
}
