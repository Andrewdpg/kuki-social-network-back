package com.kuki.social_networking.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Represents a request to create a new role in the system.
 */
@Data
public class CreateRoleRequest {
    @NotBlank(message = "Role name is mandatory")
    @Pattern(regexp = "^[A-Z]+$", message = "Invalid role name, must be in uppercase")
    private String name;
}

