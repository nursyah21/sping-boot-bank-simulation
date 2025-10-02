package com.nurs.backend.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "username is required")
    @Length(min = 4, max = 20)
    private String username;
    
    @NotBlank(message = "password is required")
    @Length(min = 8, max = 100)
    private String password;
}
