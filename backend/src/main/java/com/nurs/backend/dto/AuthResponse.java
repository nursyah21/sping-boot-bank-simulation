package com.nurs.backend.dto;

import lombok.Value;

@Value
public class AuthResponse {
    private String message;
    private String token;
}
