package com.nurs.backend.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nurs.backend.model.Account;
import com.nurs.backend.repository.AccountRepository;

import lombok.val;

@ExtendWith(MockitoExtension.class)
class AccountIdGeneratorImplTest {
  @Mock
  private AccountRepository accountRepository; 

  @InjectMocks
  private AccountIdGeneratorImpl accountIdGenerator;

  @Test
  void shouldGenerateUniqueIdOnFirstAttemp() {
    when(accountRepository.findByAccountId(anyString()))
      .thenReturn(Optional.empty());

    val newId = accountIdGenerator.generatorUniqueAccountId();

    assertNotNull(newId);
    assertEquals(10, newId.length());

    verify(accountRepository, times(1)).findByAccountId(anyString());
  }

  @Test
  void shouldRetryUntilUniqueIdIsFound() {
    when(accountRepository.findByAccountId(anyString()))
      .thenReturn(Optional.of(new Account()))
      .thenReturn(Optional.of(new Account()))
      .thenReturn(Optional.empty());
    
    val newId = accountIdGenerator.generatorUniqueAccountId();
    assertNotNull(newId);

    verify(accountRepository, times(3)).findByAccountId(anyString());
  }
}
