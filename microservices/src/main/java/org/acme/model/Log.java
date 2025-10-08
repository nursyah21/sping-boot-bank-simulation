package org.acme.model;

import java.time.Instant;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MongoEntity(collection = "logs")
public class Log extends PanacheMongoEntity {
  private String message;
  private Instant timestamp = Instant.now();
}
