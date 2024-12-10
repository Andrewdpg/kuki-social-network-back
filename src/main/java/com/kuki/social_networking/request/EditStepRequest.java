package com.kuki.social_networking.request;

import lombok.Data;
import java.util.UUID;

@Data
public class EditStepRequest {

    private UUID recipeId;
    private UUID stepId;
    private int number;
    private String description;
    private String multimediaUrl;

}
