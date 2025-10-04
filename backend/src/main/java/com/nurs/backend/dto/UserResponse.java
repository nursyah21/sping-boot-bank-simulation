package com.nurs.backend.dto;

import lombok.Value;

@Value
public class UserResponse {
  private Long userId;
  private String accountId;
  private String roles;
  private String username;
  private Long balance;
}
