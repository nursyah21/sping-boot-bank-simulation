package com.nurs.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nurs.backend.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByAccountId(String accountId);
  Optional<Account> findByUserId(Long userId);

  List<Account> findByAccountIdIn(List<String> accountIds);
}
