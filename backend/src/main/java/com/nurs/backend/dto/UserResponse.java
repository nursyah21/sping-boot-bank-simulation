package com.nurs.backend.dto;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class UserResponse {
  private Long userId;
  private String accountId;
  private String roles;
  private String username;
  private BigDecimal balance;
}
