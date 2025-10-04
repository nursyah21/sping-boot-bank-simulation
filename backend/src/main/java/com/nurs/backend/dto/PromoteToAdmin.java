package com.nurs.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class PromoteToAdmin {
    @NotBlank
    @Size(min = 4, max = 20)
    private String username;
}
