package com.kuki.social_networking.controller.rest;

import com.kuki.social_networking.mapper.StepMapper;
import com.kuki.social_networking.model.Step;
import com.kuki.social_networking.request.*;
import com.kuki.social_networking.response.StepResponse;
import com.kuki.social_networking.service.implementation.CustomUserDetails;
import com.kuki.social_networking.service.interfaces.StepService;
import com.kuki.social_networking.util.annotation.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/steps")
@RequiredArgsConstructor
public class StepController {

    private final StepService stepService;
    private final StepMapper stepMapper;

    @GetMapping
    public ResponseEntity<Page<StepResponse>> getSteps(SearchRecipeStepsRequest searchStepRequest) {

        Page<Step> steps = stepService.getStepsByRecipeId(
            searchStepRequest.getRecipeId(),
            searchStepRequest
        );

        return ResponseEntity.ok(steps.map(stepMapper::toStepResponse));
    }

    @PostMapping
    public ResponseEntity<List<StepResponse>> addStep(@AuthenticatedUser CustomUserDetails userDetails,
                                                      @RequestPart("request") CreateStepRequest createStepRequest,
                                                      @RequestPart("files") List<MultipartFile> files) {
        List<Step> steps = stepService.createStep(userDetails, createStepRequest, files);
        return ResponseEntity.ok(steps.stream().map(stepMapper::toStepResponse).toList());
    }
    @PutMapping
    public ResponseEntity<StepResponse> editStep(@AuthenticatedUser CustomUserDetails userDetails,
                                                 @RequestBody EditStepRequest editStepRequest) {
        Step step = stepService.editStep(userDetails, editStepRequest);
        return ResponseEntity.ok(stepMapper.toStepResponse(step));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStep(@AuthenticatedUser CustomUserDetails userDetails,
                                           @RequestBody DeleteStepRequest deleteStepRequest) {
        stepService.deleteStep(userDetails, deleteStepRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepResponse> getStepById(@PathVariable UUID id) {
        Step step = stepService.getStepById(id);
        return ResponseEntity.ok(stepMapper.toStepResponse(step));
    }




}
