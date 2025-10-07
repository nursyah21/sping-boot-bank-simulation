package org.acme.service;

import org.acme.dto.LogRequest;

public interface LogService {
  void saveLog(LogRequest request);
}
