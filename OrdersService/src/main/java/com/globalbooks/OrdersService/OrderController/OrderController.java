package com.globalbooks.OrdersService;

import com.globalbooks.OrdersService.dto.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("Received order for customer: " + orderRequest.getCustomerId());
        Map<String, String> response = Map.of(
                "orderId", "ORD-" + System.currentTimeMillis(),
                "status", "CREATED"
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrderById(@PathVariable String id) {
        System.out.println("Fetching order with ID: " + id);
        return ResponseEntity.ok("Details for order " + id + " would be returned here.");
    }
}