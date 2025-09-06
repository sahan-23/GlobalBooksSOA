package com.yourcompany.payments; // Make sure this package name is correct

public class Main {

    public static void main(String[] args) {
        System.out.println("--- Starting Payments Service Test ---");

        // 1. Create an instance of your PaymentsService
        PaymentsService service = new PaymentsService();

        // 2. Create some sample payment information
        String samplePaymentInfo = "{\"paymentMethod\": \"CreditCard\", \"amount\": 5000.00}";

        // 3. Call the processPayment method to test it
        // This will process the payment and send a message to the queue
        boolean success = service.processPayment(samplePaymentInfo);

        if (success) {
            System.out.println("--- Payment processing was successful ---");
        } else {
            System.out.println("--- Payment processing failed ---");
        }
    }
}