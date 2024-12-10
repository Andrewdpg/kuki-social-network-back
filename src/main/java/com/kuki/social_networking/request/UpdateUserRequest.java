package com.kuki.social_networking.request;

import com.kuki.social_networking.model.User;
import com.kuki.social_networking.util.validator.FieldValidator;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * Request object for updating a user.
 */

@Data
@Builder
public class UpdateUserRequest {
    private String username;
    private String email;
    private String fullName;
    private String country;
    private String biography;
    private Map<String, Object> socialMedia;
}