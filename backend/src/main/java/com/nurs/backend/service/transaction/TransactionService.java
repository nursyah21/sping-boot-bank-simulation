package com.nurs.backend.service.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import com.nurs.backend.dto.TransactionResponse;
import com.nurs.backend.dto.TransactionRequest;

public interface TransactionService {
  TransactionResponse transferMoney(TransactionRequest request, UserDetails userDetails);
  Page<TransactionResponse> getAllTransactions(UserDetails userDetails, Pageable pageable, String keyword);
}
