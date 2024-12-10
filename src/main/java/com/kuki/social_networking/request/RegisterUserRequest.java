package com.kuki.social_networking.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

/**
 * Request object for registering a new user.
 */
@Data
@Builder
public class RegisterUserRequest {
    @NotEmpty
    String username;

    @Email
    @NotEmpty
    String email;

    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,20}$",
        message = "La contraseña debe tener entre 8 a 20 caracteres, incluir al menos un número, una letra mayúscula, una letra minúscula y un carácter especial"
    )
    String password;

    @NotEmpty
    String fullName;

    @NotEmpty
    String country;
}