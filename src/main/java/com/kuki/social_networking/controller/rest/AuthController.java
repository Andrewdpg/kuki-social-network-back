package com.kuki.social_networking.controller.rest;

import com.kuki.social_networking.request.LoginRequest;
import com.kuki.social_networking.request.RegisterUserRequest;
import com.kuki.social_networking.response.AuthResponse;
import com.kuki.social_networking.service.interfaces.AuthService;
import com.kuki.social_networking.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterUserRequest registerRequest) {
        userService.registerUser(registerRequest);
        String token = authService.authenticateUser(registerRequest.getUsername(), registerRequest.getPassword());
        return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    }

}
