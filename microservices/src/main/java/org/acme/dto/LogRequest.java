package org.acme.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class LogRequest {
    @NotBlank(message = "message is required")
    private String message;

    @NotNull(message = "userId is required")
    private Long userId;
}
