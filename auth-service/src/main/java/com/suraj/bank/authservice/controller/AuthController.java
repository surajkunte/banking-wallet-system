package com.suraj.bank.authservice.controller;

import com.suraj.bank.authservice.dto.*;
import com.suraj.bank.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for user registration, login, token refresh, logout, and current user lookup")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @Operation(summary = "Authenticate user and return access and refresh tokens")
    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @Operation(summary = "Generate a new access token using a refresh token")
    @PostMapping("/refresh")
    public TokenResponse refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @Operation(summary = "Revoke a refresh token")
    @PostMapping("/logout")
    public String logout(@Valid @RequestBody LogoutRequest request) {
        return authService.logout(request);
    }

    @Operation(summary = "Return the currently authenticated user")
    @GetMapping("/me")
    public String me(Authentication authentication) {
        return "Logged in as: " + authentication.getName();
    }
}