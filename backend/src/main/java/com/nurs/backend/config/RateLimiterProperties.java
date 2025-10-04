package com.nurs.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "security.ratelimit")
@Data
public class RateLimiterProperties {
    private int maxRequests = 5;
    private int timeWindowMs = 60000;
}
