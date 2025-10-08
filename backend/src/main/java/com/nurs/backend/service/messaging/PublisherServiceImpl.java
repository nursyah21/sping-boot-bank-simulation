package com.nurs.backend.service.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.nurs.backend.config.RabbitConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
  private final RabbitTemplate rabbitTemplate;

  @Override
  public void publishLog(String message) {
    rabbitTemplate.convertAndSend(
        RabbitConfig.EXCHANGE_NAME,
        RabbitConfig.ROUTING_KEY,
        message);
  }

}
