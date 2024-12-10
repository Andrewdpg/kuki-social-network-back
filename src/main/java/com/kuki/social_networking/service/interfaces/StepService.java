package com.kuki.social_networking.service.interfaces;

import com.kuki.social_networking.model.Step;
import com.kuki.social_networking.request.DeleteStepRequest;
import com.kuki.social_networking.request.EditStepRequest;
import com.kuki.social_networking.request.CreateStepRequest;
import com.kuki.social_networking.request.PageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


public interface StepService {

    List<Step> createStep(UserDetails userDetails, CreateStepRequest request, List<MultipartFile> files);
    Step editStep(UserDetails userDetails, EditStepRequest request);
    void deleteStep(UserDetails userDetails, DeleteStepRequest request);
    Step getStepById(UUID stepId);

    Page<Step> getStepsByRecipeId(UUID recipeId, PageableRequest request);
}
