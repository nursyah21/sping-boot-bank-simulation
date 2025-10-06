package com.nurs.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.nurs.backend.dto.AuthRequest;
import com.nurs.backend.dto.PromoteToAdminRequest;
import com.nurs.backend.repository.UserRepository;
import com.nurs.backend.service.admin.AdminService;
import com.nurs.backend.service.auth.AuthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@DependsOn("flyway")
public class AdminInitializer implements CommandLineRunner {
  private final UserRepository userRepository;
  private final AuthService authService;
  private final AdminService adminService;

  @Override
  @Transactional
  public void run(String... args) throws Exception {

    if (userRepository.findRawByUsername("user").isEmpty()) {
      val authRequest = new AuthRequest("user", "password");

      authService.register(authRequest);

      log.info("Create new user: username:user password:password");
    }

    if (userRepository.findRawByUsername("admin").isEmpty()) {
      val authRequest = new AuthRequest("admin", "password");

      authService.register(authRequest);

      val promoteToAdmin = new PromoteToAdminRequest("admin");
      adminService.promoteToAdmin(promoteToAdmin);

      log.info("ADMIN default: username:admin password:mypassword");
    }

  }

}
