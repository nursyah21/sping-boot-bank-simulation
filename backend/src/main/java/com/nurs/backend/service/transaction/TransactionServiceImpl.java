package com.nurs.backend.service.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nurs.backend.dto.TransactionRequest;
import com.nurs.backend.dto.TransactionResponse;
import com.nurs.backend.exception.CustomException;
import com.nurs.backend.model.Transaction;
import com.nurs.backend.repository.AccountRepository;
import com.nurs.backend.repository.TransactionRepository;
import com.nurs.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.val;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final UserRepository userRepository;

  @Override
  @Transactional
  public TransactionResponse transferMoney(TransactionRequest request, UserDetails userDetails) {

    val user = userRepository.findProfileDataByUsername(userDetails.getUsername())
        .orElseThrow(() -> new CustomException("User not found"));

    if (user.getAccount().getAccountId().equals(request.getDestinationId())) {
      throw new CustomException("Source and destination accounts cannot be the same");
    }

    val isAdmin = user.getRoles().stream()
        .anyMatch(role -> "ADMIN".equalsIgnoreCase(role.getName()));

    val destinationAccount = accountRepository.findByAccountId(request.getDestinationId())
        .orElseThrow(() -> new CustomException("Destination Id not found"));

    if (!isAdmin && user.getAccount().getBalance().compareTo(request.getAmount()) < 0) {
      throw new CustomException("Insufficient balance in source account");
    }

    if (!isAdmin) {
      val remainingBalance = user.getAccount().getBalance().subtract(request.getAmount());
      user.getAccount().setBalance(remainingBalance);
    }

    destinationAccount.setBalance(destinationAccount.getBalance().add(request.getAmount()));

    accountRepository.save(user.getAccount());
    accountRepository.save(destinationAccount);

    val transaction = new Transaction();
    transaction.setAmount(request.getAmount());
    transaction.setSourceAccount(user.getAccount());
    transaction.setDestinationAccount(destinationAccount);

    transactionRepository.save(transaction);

    return new TransactionResponse(
        request.getAmount(),
        transaction.getId(),
        transaction.getSourceAccount().getAccountId(),
        transaction.getDestinationAccount().getAccountId(),
        transaction.getCreatedAt());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TransactionResponse> getAllTransactions(
      UserDetails userDetails, Pageable pageable, String keyword) {
    val user = userRepository.findProfileDataByUsername(userDetails.getUsername())
        .orElseThrow(() -> new CustomException("User not found"));

    val isAdmin = user.getRoles().stream()
        .anyMatch(role -> "ADMIN".equalsIgnoreCase(role.getName()));

    Page<Transaction> pageTransaction;

    val hasKeyword = keyword != null && !keyword.isBlank();

    if (isAdmin) {
      if (hasKeyword) {
        pageTransaction = transactionRepository.searchByAdmin(keyword, pageable);
      } else {
        pageTransaction = transactionRepository.searchByAdmin(pageable);
      }
    } else {
      if (hasKeyword) {
        pageTransaction = transactionRepository.searchByAccountId(keyword, pageable, user.getAccount().getAccountId());
      } else {
        pageTransaction = transactionRepository.searchByAccountId(pageable, user.getAccount().getAccountId());
      }
    }

    return pageTransaction.map(transaction -> {
      return new TransactionResponse(
          transaction.getAmount(),
          transaction.getId(),
          transaction.getSourceAccount().getAccountId(),
          transaction.getDestinationAccount().getAccountId(),
          transaction.getCreatedAt());
    });
  }
}
