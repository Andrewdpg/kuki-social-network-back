package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Step;
import com.kuki.social_networking.response.StepResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-05T13:27:04-0500",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class StepMapperImpl implements StepMapper {

    @Override
    public StepResponse toStepResponse(Step step) {
        if ( step == null ) {
            return null;
        }

        StepResponse stepResponse = new StepResponse();

        stepResponse.setId( step.getId() );
        stepResponse.setNumber( step.getNumber() );
        stepResponse.setDescription( step.getDescription() );
        stepResponse.setMultimediaUrl( step.getMultimediaUrl() );
        stepResponse.setEstimatedTime( step.getEstimatedTime() );

        return stepResponse;
    }
}
