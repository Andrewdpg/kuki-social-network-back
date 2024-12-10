package com.kuki.social_networking.response;

import lombok.Data;

import java.time.Duration;
import java.util.UUID;

@Data
public class StepResponse {

    private UUID id;
    private int number;
    private String description;
    private String multimediaUrl;
    private Duration estimatedTime;

}
