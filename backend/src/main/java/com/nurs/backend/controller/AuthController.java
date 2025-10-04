package com.nurs.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nurs.backend.dto.AuthRequest;
import com.nurs.backend.dto.GenericResponse;
import com.nurs.backend.dto.LoginResponse;
import com.nurs.backend.dto.UserResponse;
import com.nurs.backend.model.User;
import com.nurs.backend.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<Void> register(
            @Valid @RequestBody AuthRequest request) {
        authService.register(request);
        return new GenericResponse<>("create account success", null);
    }

    @PostMapping("/login")
    public GenericResponse<LoginResponse> login(
            @Valid @RequestBody AuthRequest request) {
        return new GenericResponse<>(
                "login success",
                authService.login(request));
    }

    @GetMapping("/profile")
    public GenericResponse<UserResponse> profile(
           @AuthenticationPrincipal User userDetails
    ) {
        return new GenericResponse<>(
            "get profile success",
            authService.profile(userDetails)
        );
    }
}
