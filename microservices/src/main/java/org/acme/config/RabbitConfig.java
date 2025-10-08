package org.acme.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class RabbitConfig {

    public static final String EXCHANGE_NAME = "logs";
    public static final String QUEUE_NAME = "logs_queue";
    public static final String ROUTING_KEY = "logs.key";

    @Produces
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory("127.0.0.1", 5672);
        factory.setUsername("rabbitmq");
        factory.setPassword("password");
        return factory;
    }

}
