package com.nurs.backend.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeRequest {
    @NotBlank(message = "firstName is required")
    @Length(min = 4, max = 20)
    private String firstName;
    
    @NotBlank(message = "lastName is required")
    @Length(min = 4, max = 20)
    private String lastName;

    @NotBlank(message = "emailId is required")
    @Email(message = "email must be valid")
    private String emailId;
}
