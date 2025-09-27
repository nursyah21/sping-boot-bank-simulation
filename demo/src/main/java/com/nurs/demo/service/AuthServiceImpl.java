package com.nurs.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nurs.demo.dto.AuthRequest;
import com.nurs.demo.dto.AuthResponse;
import com.nurs.demo.exception.CustomException;
import com.nurs.demo.model.User;
import com.nurs.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(AuthRequest request) {
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

        return new AuthResponse(jwtToken);
    }

    @Override
    public AuthResponse register(AuthRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(u -> {
            throw new CustomException("username already registered");
        });

        val user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        val jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

}
