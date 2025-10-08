package org.acme.messaging;

import org.acme.config.RabbitConfig;
import org.acme.service.LogService;
import org.apache.camel.builder.RouteBuilder;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.val;

@ApplicationScoped
@RequiredArgsConstructor
public class RabbitLogRoute extends RouteBuilder {
    private final LogService logService;

    @Override
    public void configure() {
        from("spring-rabbitmq:"+RabbitConfig.EXCHANGE_NAME
            +"?queues="+RabbitConfig.QUEUE_NAME
            +"&routingKey="+RabbitConfig.ROUTING_KEY
            +"&autoDeclare=false")
            .routeId("rabbit-log-consumer")
            .process(exchange -> {
                val body = exchange.getIn().getBody(String.class);
                
                logService.saveLog(body);
            });
    }
}
