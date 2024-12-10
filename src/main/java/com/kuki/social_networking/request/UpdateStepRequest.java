package com.kuki.social_networking.request;

import com.kuki.social_networking.model.Step;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateStepRequest {

    private UUID StepID;
    private int number;
    private String description;

    public Step updateStep(Step step){

        if (number >= 0){
            step.setNumber(number);
        }
        if (description != null){
            step.setDescription(description);
        }

        return step;

    }
}
