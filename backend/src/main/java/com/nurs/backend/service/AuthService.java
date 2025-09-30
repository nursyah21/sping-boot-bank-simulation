package com.nurs.backend.service;

import java.util.Map;

import com.nurs.backend.dto.AuthRequest;

public interface AuthService {
    void register(AuthRequest request);
    Map<String, String> login(AuthRequest request);
}
