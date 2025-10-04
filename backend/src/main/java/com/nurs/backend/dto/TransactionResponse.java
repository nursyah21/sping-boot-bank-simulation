package com.nurs.backend.dto;

import java.time.LocalDateTime;

import lombok.Value;

@Value
public class TransactionResponse {
  private Long amountTransfer;
  private Long TransactionId;
  private String sourceId;
  private String destionationId;
  private LocalDateTime createdAt;
}
