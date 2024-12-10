package com.kuki.social_networking.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreateStepRequest {

    List<StepDTO> steps;
    UUID recipeId;
}
