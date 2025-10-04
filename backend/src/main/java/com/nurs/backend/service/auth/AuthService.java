package com.nurs.backend.service.auth;

import org.springframework.security.core.userdetails.UserDetails;

import com.nurs.backend.dto.AuthRequest;
import com.nurs.backend.dto.LoginResponse;
import com.nurs.backend.dto.UserResponse;

public interface AuthService {
    void register(AuthRequest request);
    LoginResponse login(AuthRequest request);
    UserResponse profile(UserDetails userDetails);
}
