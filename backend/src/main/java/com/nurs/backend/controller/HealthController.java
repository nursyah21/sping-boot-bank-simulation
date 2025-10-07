package com.nurs.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nurs.backend.dto.GenericResponse;

@RestController
@RequestMapping("/health")
public class HealthController {
  
  @GetMapping
  public GenericResponse<Void> getHealth() {
    return new GenericResponse<>("ok", null);
  }
}
