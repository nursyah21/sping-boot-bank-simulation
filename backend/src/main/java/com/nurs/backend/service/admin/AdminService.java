package com.nurs.backend.service.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import com.nurs.backend.dto.PromoteToAdminRequest;
import com.nurs.backend.dto.UserResponse;

public interface AdminService {
  void promoteToAdmin(PromoteToAdminRequest request);
  void deactivateAccount(UserDetails userDetails, Long userId);
  void activateAccount(Long userId);
  Page<UserResponse> getAllUsers(Pageable pageable, String keyword, Boolean isDeleted);
  UserResponse getUserById(Long userId, Boolean isDeleted);
}
