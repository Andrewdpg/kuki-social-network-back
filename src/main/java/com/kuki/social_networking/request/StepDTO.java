package com.kuki.social_networking.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StepDTO {
    int stepNumber;
    String description;
    int estimatedTime;
}
