package com.kuki.social_networking.mapper;

import com.kuki.social_networking.model.Step;
import com.kuki.social_networking.response.StepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface StepMapper {

    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "number", target = "number"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "multimediaUrl", target = "multimediaUrl"),
        @Mapping(source = "estimatedTime", target = "estimatedTime")
    })
    StepResponse toStepResponse(Step step);

}
