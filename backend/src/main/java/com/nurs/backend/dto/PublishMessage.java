package com.nurs.backend.dto;

import java.time.Instant;

import lombok.Value;

@Value
public class PublishMessage {
    private String serviceName;  
    private String action;       
    private Object payload;     
    private Instant timestamp;
}
