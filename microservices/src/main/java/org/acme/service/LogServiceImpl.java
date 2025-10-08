package org.acme.service;

import java.util.List;

import org.acme.model.Log;
import org.acme.repository.LogRepository;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.val;

@ApplicationScoped
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

  private final LogRepository logRepository;

  @Override
  public void saveLog(String message) {
    val log = new Log();
    log.setMessage(message);
    logRepository.persist(log);
  }

  @Override
  public List<Log> getLogsPaged(int page, int size) {
    return logRepository.findAll(Sort.descending("timestamp"))
        .page(Page.of(page, size))
        .list();
  }

  @Override
  public Long totalLogs() {
    return logRepository.count();
  }
  
}
