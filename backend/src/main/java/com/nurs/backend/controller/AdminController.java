package com.nurs.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nurs.backend.dto.GenericResponse;
import com.nurs.backend.dto.PromoteToAdminRequest;
import com.nurs.backend.dto.UserResponse;
import com.nurs.backend.service.admin.AdminService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  private final AdminService adminService;

  @GetMapping("/account")
  public GenericResponse<Page<UserResponse>> getAllUsers(
    @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
    Pageable pageable,
    @RequestParam(required = false) String keyword,
    @RequestParam(required = false) Boolean isDeleted
  ) {
    return new GenericResponse<>(
      "get all user success",
      adminService.getAllUsers(pageable, keyword, isDeleted)
    );
  }

  @GetMapping("/account/{userId}")
  public GenericResponse<UserResponse> getUser(
    @PathVariable Long userId,
    @RequestParam(required = false) Boolean isDeleted
  ) {
    return new GenericResponse<>(
      "get user success",
      adminService.getUserById(userId, isDeleted)
    );
  }

  @DeleteMapping("/account/deactivate/{userId}")
  public GenericResponse<Void> deactivateUser(
    @AuthenticationPrincipal UserDetails userDetails,
    @PathVariable Long userId
  ) {
    adminService.deactivateAccount(userDetails, userId);
    return new GenericResponse<>("success deactivate account user", null);
  }

  @PutMapping("/account/activate/{userId}")
  public GenericResponse<Void> activateUser(
    @PathVariable Long userId
  ) {
    adminService.activateAccount(userId);
    return new GenericResponse<>("success activate account user", null);
  }

  @PutMapping("/promote")
  public GenericResponse<Void> promoteToAdmin(
      @Valid @RequestBody PromoteToAdminRequest request
  ) {
    adminService.promoteToAdmin(request);
    return new GenericResponse<>("success promote user to admin", null);
  }
}
