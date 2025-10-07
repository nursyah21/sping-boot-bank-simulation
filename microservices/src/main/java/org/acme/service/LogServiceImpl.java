package org.acme.service;

import java.time.Instant;

import org.acme.dto.LogRequest;
import org.acme.model.Log;
import org.acme.repository.LogRepository;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.val;

@ApplicationScoped
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

  private final LogRepository logRepository;

  @Override
  public void saveLog(LogRequest request) {
    val log = new Log();
    log.setTimestamp(Instant.now());
    log.setMessage(request.getMessage());
    log.setUserId(request.getUserId());

    logRepository.persist(log);
  }
  
}
