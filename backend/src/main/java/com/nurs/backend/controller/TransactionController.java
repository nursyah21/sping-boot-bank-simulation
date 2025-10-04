package com.nurs.backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nurs.backend.dto.GenericResponse;
import com.nurs.backend.dto.TransactionRequest;
import com.nurs.backend.dto.TransactionResponse;
import com.nurs.backend.model.User;
import com.nurs.backend.service.transaction.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {
  private final TransactionService transactionService;

  @GetMapping
  public GenericResponse<Page<TransactionResponse>> getAllTransactions(
    @AuthenticationPrincipal User user,  
    @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
    Pageable pageable,
    @RequestParam(required = false) String keyword
  ) {
    return new GenericResponse<>(
      "get all transaction", 
      transactionService.getAllTransactions(user, pageable, keyword));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GenericResponse<TransactionResponse> TransferMoney(
    @Valid @RequestBody TransactionRequest request,
    @AuthenticationPrincipal User userDetails
  ) {

    return new GenericResponse<>(
      "transfer money success", 
      transactionService.transferMoney(request, userDetails)
    );
  }
}
