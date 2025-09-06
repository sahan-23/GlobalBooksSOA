package com.globalbooks.ordersservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "order_exchange";
    public static final String PAYMENTS_QUEUE = "payments_queue";
    public static final String SHIPPING_QUEUE = "shipping_queue";

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue paymentsQueue() {
        // The 'true' means the queue is durable (survives broker restarts)
        return new Queue(PAYMENTS_QUEUE, true);
    }

    @Bean
    Queue shippingQueue() {
        return new Queue(SHIPPING_QUEUE, true);
    }

    // Bind the payments queue to the exchange
    @Bean
    Binding paymentsBinding(Queue paymentsQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(paymentsQueue).to(exchange);
    }

    // Bind the shipping queue to the exchange
    @Bean
    Binding shippingBinding(Queue shippingQueue, FanoutExchange exchange) {
        return BindingBuilder.bind(shippingQueue).to(exchange);
    }
}.