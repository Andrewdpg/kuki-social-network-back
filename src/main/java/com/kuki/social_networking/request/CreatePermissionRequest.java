package com.kuki.social_networking.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreatePermissionRequest {
    @NotBlank(message = "Permission name is mandatory")
    @Pattern(regexp = "^[A-Z]+_[A-Z_]+$", message = "Invalid permission name, must be in uppercase and separated by underscores")
    private String name;
}