package com.nurs.demo.service;

import com.nurs.demo.dto.AuthRequest;
import com.nurs.demo.dto.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}
