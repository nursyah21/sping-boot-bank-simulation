package com.nurs.backend.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nurs.backend.dto.AuthRequest;
import com.nurs.backend.exception.CustomException;
import com.nurs.backend.mapper.AuthMapper;
import com.nurs.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;

    @Override
    public Map<String, String> login(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (Exception e) {
            throw new CustomException("Credential invalid");
        }

        val username = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> {
                throw new CustomException("User not found after successful authenticated");
            });
 
        val jwtToken = jwtService.generateToken(username);

        return Map.of("token", jwtToken);
    }

    @Override
    public void register(AuthRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new CustomException("username already registered");
        });

        val user = authMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

}
