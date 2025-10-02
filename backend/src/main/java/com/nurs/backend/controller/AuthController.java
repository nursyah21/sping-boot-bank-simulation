package com.nurs.backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nurs.backend.dto.AuthRequest;
import com.nurs.backend.dto.GenericResponse;
import com.nurs.backend.service.AuthService;

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
        @Valid @RequestBody AuthRequest request
    ) {
        authService.register(request);
        return new GenericResponse<>("create account success", null) ;
    }

    @PostMapping("/login")
    public GenericResponse<Map<String, String>> login(
        @Valid @RequestBody AuthRequest request
    ) {
        return new GenericResponse<>(
            "login success", 
            authService.login(request)
        );
    }
}
