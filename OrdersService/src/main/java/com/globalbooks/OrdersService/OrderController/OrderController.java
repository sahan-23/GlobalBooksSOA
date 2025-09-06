package com.globalbooks.ordersservice.controller;

import com.globalbooks.ordersservice.config.RabbitMQConfig; // <-- Import config
import com.globalbooks.ordersservice.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate; // <-- Import RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired; // <-- Import Autowired
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Map<String, Order> orders = new HashMap<>();

    // --- NEW CODE STARTS HERE ---
    @Autowired
    private RabbitTemplate rabbitTemplate;
    // --- NEW CODE ENDS HERE ---

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        String orderId = UUID.randomUUID().toString();
        order.setOrderId(orderId);
        order.setStatus("PROCESSING");

        orders.put(orderId, order);

        // --- NEW CODE STARTS HERE ---
        // Send a message to the exchange with the new order ID
        System.out.println("Publishing message for new order: " + orderId);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "", orderId);
        // The "" is the routing key, which is ignored by fanout exchanges.
        // --- NEW CODE ENDS HERE ---

        System.out.println("New order created: " + order);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") String id) {
        Order order = orders.get(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}