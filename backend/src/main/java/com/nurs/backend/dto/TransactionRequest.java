package com.nurs.backend.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class TransactionRequest {
    @NotNull(message = "amount is required")
    @Min(1)
    @Max(100_000_000)
    private BigDecimal amount;

    @NotBlank(message = "destinationId is required")
    @Size(min = 10, max = 10)
    private String destinationId;
}
