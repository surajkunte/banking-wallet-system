package com.suraj.bank.authservice.controller;

import com.suraj.bank.authservice.dto.*;
import com.suraj.bank.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public String me(Principal principal) {
        return "Logged in as: " + principal.getName();
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public String logout(@Valid @RequestBody LogoutRequest request) {
        return authService.logout(request);
    }
}