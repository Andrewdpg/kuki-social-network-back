package com.kuki.social_networking.response;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimpleUserResponse {
    private String username;
    private String photoUrl;
    private String fullName;
    private String biography;
    private Map<String, Object> socialMedia;
    private String email;
    private String country;
    private boolean followed;
}