package com.nurs.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Value;

@Value
public class TransactionResponse {
  private BigDecimal amountTransfer;
  private Long TransactionId;
  private String sourceId;
  private String destinationId;
  private LocalDateTime createdAt;
}
