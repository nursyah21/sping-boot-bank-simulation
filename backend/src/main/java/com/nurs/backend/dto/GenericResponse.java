package com.nurs.backend.dto;

import lombok.Value;

@Value
public class GenericResponse<T> {
    private String message;
    private T data;
}
