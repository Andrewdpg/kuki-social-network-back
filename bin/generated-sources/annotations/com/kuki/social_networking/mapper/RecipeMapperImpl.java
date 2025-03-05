package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.response.RecipeResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public RecipeResponse toRecipeResponse(Recipe recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeResponse recipeResponse = new RecipeResponse();

        recipeResponse.setId( recipe.getId() );
        recipeResponse.setTitle( recipe.getTitle() );
        recipeResponse.setDescription( recipe.getDescription() );
        recipeResponse.setPhotoUrl( recipe.getPhotoUrl() );
        recipeResponse.setPublishDate( recipe.getPublishDate() );
        recipeResponse.setEstimatedTime( recipe.getEstimatedTime() );
        recipeResponse.setCountry( mapCountry( recipe.getCountry() ) );
        recipeResponse.setDifficulty( recipe.getDifficulty() );
        recipeResponse.setTags( mapTags( recipe.getTags() ) );
        recipeResponse.setLikes( recipe.getLikesCount() );
        recipeResponse.setComments( recipe.getCommentsCount() );
        recipeResponse.setRecipeOwner( mapUser( recipe.getRecipeOwner() ) );

        return recipeResponse;
    }
}
