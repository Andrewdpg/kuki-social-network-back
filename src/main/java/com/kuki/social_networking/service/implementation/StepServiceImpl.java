package com.kuki.social_networking.service.implementation;

import com.kuki.social_networking.exception.StepNotFoundException;
import com.kuki.social_networking.model.Recipe;
import com.kuki.social_networking.model.Step;
import com.kuki.social_networking.model.User;
import com.kuki.social_networking.repository.RecipeRepository;
import com.kuki.social_networking.repository.StepRepository;
import com.kuki.social_networking.request.CreateStepRequest;
import com.kuki.social_networking.request.DeleteStepRequest;
import com.kuki.social_networking.request.EditStepRequest;
import com.kuki.social_networking.request.PageableRequest;
import com.kuki.social_networking.service.interfaces.ImageService;
import com.kuki.social_networking.service.interfaces.RecipeService;
import com.kuki.social_networking.service.interfaces.StepService;
import com.kuki.social_networking.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.kuki.social_networking.util.EntitySpecs.getPageable;

@Service
@RequiredArgsConstructor
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepository;
    private final RecipeService recipeService;
    private final ImageService imageService;
    private final UserService userService;
    private final RecipeRepository recipeRepository;

    /**
     * Adds a new step for a recipe.
     *
     * @param userDetails The authenticated user creating the step.
     * @param request     The step data to be created.
     * @return The added step to the recipe.
     */
    @Override
    public List<Step> createStep(UserDetails userDetails, CreateStepRequest request, List<MultipartFile> files) {

        Recipe recipe = recipeService.getRecipeOwnedByUser(userDetails, request.getRecipeId());

        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < request.getSteps().size(); i++) {
            String imageUrl;
            try {
                imageUrl = imageService.uploadImage(files.get(i));
            } catch (IOException e) {
                throw new IllegalArgumentException("Invalid image file");
            }

            Step step = Step.builder()
                .number(request.getSteps().get(i).getStepNumber())
                .description(request.getSteps().get(i).getDescription())
                .estimatedTime(Duration.ofMinutes(request.getSteps().get(i).getEstimatedTime()))
                .recipe(recipe)
                .multimediaUrl(imageUrl)
                .build();

            steps.add(step);
        }

        stepRepository.saveAll(steps);

        recipe = recipeService.getRecipeOwnedByUser(userDetails, request.getRecipeId());

        recipe.setEstimatedTime(recipe.getSteps().stream()
            .map(Step::getEstimatedTime)
            .reduce(Duration.ZERO, Duration::plus));

        recipeRepository.save(recipe);

        return recipe.getSteps();
    }

    /**
     * Edits an existing step for a recipe.
     *
     * @param userDetails The authenticated user editing the step.
     * @param request     The updated step data.
     * @return The updated step.
     */
    @Override
    public Step editStep(UserDetails userDetails, EditStepRequest request) {
        User user = userService.getUserByUsername(userDetails.getUsername());

        // TODO: change this exception to a custom exception
        user.getRecipes().stream()
            .filter(r -> r.getId().equals(request.getRecipeId()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User does not have access to this recipe"));

        Step step = getStepById(request.getStepId());

        step.setNumber(request.getNumber());
        step.setDescription(request.getDescription());

        stepRepository.save(step);
        return step;
    }

    /**
     * Deletes a step from a recipe.
     *
     * @param userDetails The authenticated user deleting the step.
     * @param request     The step data to be deleted.
     */
    @Override
    public void deleteStep(UserDetails userDetails, DeleteStepRequest request) {

        User user = userService.getUserByUsername(userDetails.getUsername());

        // TODO: change this exception to a custom exception
        user.getRecipes().stream()
            .filter(r -> r.getId().equals(request.getRecipeId()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("User does not have access to this recipe"));

        Step step = getStepById(request.getStepId());

        // TODO: Deletes the associated imaged from this step
        stepRepository.delete(step);
    }

    /**
     * Retrieves a step by its ID.
     *
     * @param stepId The ID of the step to be retrieved.
     * @return The step with the given ID.
     */
    @Override
    public Step getStepById(UUID stepId) {
        return stepRepository.findById(stepId)
            .orElseThrow(() -> new StepNotFoundException("Step not found"));
    }

    /**
     * Retrieves all steps for a recipe.
     *
     * @param recipeId The ID of the recipe to retrieve steps for.
     * @param request  The pagination data.
     * @return The steps for the recipe.
     */
    @Override
    public Page<Step> getStepsByRecipeId(UUID recipeId, PageableRequest request) {
        return stepRepository.findAllByRecipeId(recipeId, getPageable(request));
    }

    // TODO: add the exception handler for the custom exception


}
