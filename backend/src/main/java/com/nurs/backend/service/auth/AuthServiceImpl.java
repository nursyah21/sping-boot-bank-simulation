package com.nurs.backend.service.auth;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nurs.backend.dto.AuthRequest;
import com.nurs.backend.dto.LoginResponse;
import com.nurs.backend.dto.UserResponse;
import com.nurs.backend.exception.CustomException;
import com.nurs.backend.model.Account;
import com.nurs.backend.model.Role;
import com.nurs.backend.model.User;
import com.nurs.backend.repository.RoleRepository;
import com.nurs.backend.repository.UserRepository;
import com.nurs.backend.service.security.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountIdGenerator accountIdGenerator;


    @Override
    public LoginResponse login(AuthRequest request) {
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
                
        return new LoginResponse(jwtToken, jwtService.getExpiration());
    }

    @Override
    public UserResponse profile(UserDetails userDetails) {
        if (userDetails == null) {
            throw new CustomException("not authenticated");
        }

        val user = userRepository.findProfileDataByUsername(userDetails.getUsername())
            .orElseThrow(() -> new CustomException("User not found:"+userDetails.getUsername()));

        val account = user.getAccount(); 

        val roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));

        return new UserResponse(
            user.getId(), 
            account.getAccountId(), 
            roles, 
            user.getUsername(), 
            account.getBalance());
    }

    @Override
    @Transactional
    public void register(AuthRequest request) {
        userRepository.findRawByUsername(request.getUsername()).ifPresent(u -> {
            throw new CustomException("username already registered");
        });

        val user = new User();
        user.setUsername(request.getUsername());

        val userRole = roleRepository.findByName("USER")
            .orElseThrow(() -> new CustomException(
                "Role USER Not Found"
            ));

        val newRoleSet = new HashSet<Role>();
        newRoleSet.add(userRole);
        user.setRoles(newRoleSet);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        val uniqueAccountId = accountIdGenerator.generatorUniqueAccountId();
        val newAccount = new Account();
        newAccount.setAccountId(uniqueAccountId);
        newAccount.setUser(user);

        user.setAccount(newAccount);

        userRepository.save(user);
    }

}
