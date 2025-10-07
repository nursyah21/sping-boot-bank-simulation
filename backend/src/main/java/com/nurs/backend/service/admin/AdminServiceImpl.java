package com.nurs.backend.service.admin;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nurs.backend.dto.PromoteToAdminRequest;
import com.nurs.backend.dto.UserResponse;
import com.nurs.backend.exception.CustomException;
import com.nurs.backend.model.Role;
import com.nurs.backend.model.User;
import com.nurs.backend.repository.RoleRepository;
import com.nurs.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;

  @Override
  @Transactional
  public void promoteToAdmin(PromoteToAdminRequest request) {
    val user = userRepository.findByUsername(request.getUsername())
        .orElseThrow(() -> {
          throw new CustomException("Username not found");
        });

    val isAdmin = user.getRoles().stream()
        .anyMatch(r -> "ADMIN".equalsIgnoreCase(r.getName()));

    if (isAdmin) {
      throw new CustomException("user already admin");
    }

    val role = roleRepository.findByName("ADMIN")
        .orElseThrow(() -> {
          throw new CustomException("ADMIN role not found");
        });

    val newRoleSet = new HashSet<Role>();
    newRoleSet.add(role);
    user.setRoles(newRoleSet);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void deactivateAccount(UserDetails userDetails, Long targetUserId) {
    val currentUser = userRepository.findByUsername(userDetails.getUsername())
        .orElseThrow(() -> new CustomException("Authenticated User id not found"));

    val targetUser = userRepository.findRawById(targetUserId)
        .orElseThrow(() -> new CustomException("Target User id not found"));

    if (currentUser.getId().equals(targetUser.getId())) {
      throw new CustomException("Self deactivate not allowed");
    }

    if (targetUser.isDeleted()) {
      throw new CustomException("User already deactivate");
    }

    targetUser.setDeleted(true);
    userRepository.save(targetUser);
  }

  @Override
  @Transactional
  public void activateAccount(Long userId) {
    val targetUser = userRepository.findRawById(userId)
        .orElseThrow(() -> new CustomException("User id not found"));

    if (!targetUser.isDeleted()) {
      throw new CustomException("User already activate");
    }

    targetUser.setDeleted(false);
    userRepository.save(targetUser);
  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getUserById(Long userId, Boolean isDeleted) {
    User user;
    if (Boolean.TRUE.equals(isDeleted)) {
      user = userRepository.findProfileDataByUserIdDeletedUser(userId)
          .orElseThrow(() -> new CustomException("User id not found"));
    } else {
      user = userRepository.findProfileDataByUserId(userId)
          .orElseThrow(() -> new CustomException("User id not found"));
    }

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
  @Transactional(readOnly = true)
  public Page<UserResponse> getAllUsers(Pageable pageable, String keyword, Boolean isdeleted) {
    Page<User> userPage;

    val hasKeyword = keyword != null && !keyword.isBlank();

    if (Boolean.TRUE.equals(isdeleted)) {
      if (hasKeyword) {
        userPage = userRepository.searchFullProfileDataDeletedUsers(pageable);
      }
      userPage = userRepository.searchFullProfileDataDeletedUsers(keyword, pageable);
    } else {
      if (hasKeyword) {
        userPage = userRepository.searchFullProfileData(keyword, pageable);
      } else {
        userPage = userRepository.searchFullProfileData(pageable);
      }
    }

    return userPage.map(user -> {
      val roles = user.getRoles().stream()
          .map(Role::getName)
          .collect(Collectors.joining(","));
      val account = user.getAccount();

      return new UserResponse(
          user.getId(),
          account.getAccountId(),
          roles,
          user.getUsername(),
          account.getBalance());
    });
  }
}
