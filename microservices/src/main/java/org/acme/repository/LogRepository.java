package org.acme.repository;

import org.acme.model.Log;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LogRepository implements PanacheMongoRepository<Log> {
  
}
