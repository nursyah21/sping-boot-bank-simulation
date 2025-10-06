package com.nurs.backend.dto;

import java.util.Date;

import lombok.Value;

@Value
public class LoginResponse {
  private String token;
  private Date expiresIn;
}
