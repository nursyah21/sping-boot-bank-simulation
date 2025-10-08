package org.acme.service;

import java.util.List;

import org.acme.model.Log;


public interface LogService {
  void saveLog(String message);
  List<Log> getLogsPaged(int page, int size);
  Long totalLogs();
}
