package com.digitinary.customerservice.event.publisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class CustomerEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private DirectExchange directExchange;

    @InjectMocks
    private CustomerEventPublisher publisher;

    @BeforeEach
    void setUp() {
        openMocks(this);
        ReflectionTestUtils.setField(publisher, "createdRoutingKey", "customer.created");
        ReflectionTestUtils.setField(publisher, "deletedRoutingKey", "customer.deleted");
    }

    @Test
    void publishCustomerCreatedTest() {

    }

    @Test
    void publishCustomerDeletedTest() {
    }
}