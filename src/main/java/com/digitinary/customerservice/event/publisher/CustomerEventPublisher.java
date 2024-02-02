package com.digitinary.customerservice.event.publisher;

import com.digitinary.customerservice.event.model.CustomerEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final DirectExchange directExchange;

    @Value("${rabbitmq.routingKeys.created}")
    private String createdRoutingKey;

    @Value("${rabbitmq.routingKeys.deleted}")
    private String deletedRoutingKey;

    @Autowired
    public CustomerEventPublisher(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.rabbitTemplate = rabbitTemplate;

        this.directExchange = directExchange;
    }
    public void publishCustomerCreated(CustomerEvent event) {
        log.debug("publishing customer created event with values {}", event);
        rabbitTemplate.convertAndSend(directExchange.getName(), createdRoutingKey, event);
    }

    public void publishCustomerDeleted(CustomerEvent event) {
        log.debug("publishing customer deleted event with values {}", event);
        rabbitTemplate.convertAndSend(directExchange.getName(), deletedRoutingKey, event);
    }
}
