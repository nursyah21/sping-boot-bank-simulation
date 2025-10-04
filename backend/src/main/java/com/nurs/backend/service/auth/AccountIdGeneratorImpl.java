package com.nurs.backend.service.auth;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nurs.backend.exception.CustomException;
import com.nurs.backend.repository.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class AccountIdGeneratorImpl implements AccountIdGenerator {
  private final AccountRepository accountRepository;

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public String generatorUniqueAccountId() {
    int maxAttemp = 10;
    int attemp = 0;
    val min = 1000_000_000L;
    val max = 9999_999_999L;

    do {
      attemp++;

      val randomNum = ThreadLocalRandom.current().nextLong(min, max+1);
      val newId = String.valueOf(randomNum);
      if(accountRepository.findByAccountId(newId).isEmpty()) {
        return newId;
      }
    }while(attemp < maxAttemp);
    
    throw new CustomException("Fail to generate account id");
  }
}
