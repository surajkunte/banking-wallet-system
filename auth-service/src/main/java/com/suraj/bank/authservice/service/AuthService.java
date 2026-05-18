package com.suraj.bank.authservice.service;

import com.suraj.bank.authservice.dto.*;
import com.suraj.bank.authservice.entity.User;
import com.suraj.bank.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.suraj.bank.authservice.security.JwtService;

import com.suraj.bank.authservice.entity.RefreshToken;
import com.suraj.bank.authservice.repository.RefreshTokenRepository;

import java.time.LocalDateTime;
import java.util.UUID;

import com.suraj.bank.authservice.entity.RefreshToken;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .role("USER")
                .mfaEnabled(false)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate access token (JWT)
        String accessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        // Generate refresh token
        String refreshTokenValue = UUID.randomUUID().toString();

        // Save refresh token to database
        RefreshToken refreshToken = RefreshToken.builder()
                .token(refreshTokenValue)
                .user(user)
                .expiresAt(LocalDateTime.now().plusDays(30))
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshToken);

        // Return both tokens
        return new LoginResponse(accessToken, refreshTokenValue);
    }

    public TokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (refreshToken.isRevoked()) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token has expired");
        }

        User user = refreshToken.getUser();

        String newAccessToken = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return new TokenResponse(newAccessToken);
    }

    public String logout(LogoutRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        return "Logged out successfully";
    }

}