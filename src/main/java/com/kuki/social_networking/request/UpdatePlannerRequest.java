package com.kuki.social_networking.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdatePlannerRequest {

    String name;
    String description;
    Boolean isPublic;

}