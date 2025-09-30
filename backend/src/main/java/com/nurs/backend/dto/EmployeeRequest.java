package com.nurs.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequest {
    @NotBlank(message = "firstName is required")
    private String firstName;
    
    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "emailId is required")
    @Email(message = "email must be valid")
    private String emailId;
}
