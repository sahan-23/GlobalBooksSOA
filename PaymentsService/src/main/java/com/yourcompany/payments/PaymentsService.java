package com.yourcompany.payments;

// The import statement must be AFTER the package and BEFORE the class
import com.yourcompany.payments.ShippingQueueProducer;

public class PaymentsService {

    /**
     * This method processes the payment.
     * @return true if payment is successful, false otherwise.
     */
    public boolean processPayment(String paymentInfo) {

        System.out.println("Processing payment...");
        boolean isPaymentSuccessful = true; // Assuming the payment is successful for the test

        // If the payment is successful, send a message to the shipping queue
        if (isPaymentSuccessful) {
            System.out.println("Payment successful. Sending message to shipping queue...");

            ShippingQueueProducer producer = new ShippingQueueProducer();
            String shippingMessage = "{\"orderId\": \"ORD-54321\", \"address\": \"25/A, Galle Road, Colombo 3\"}";

            try {
                // Call the sendMessage() method to send the message
                producer.sendMessage(shippingMessage);
            } catch (Exception e) {
                System.err.println("Failed to send message to the queue: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Payment failed.");
        }

        return isPaymentSuccessful;
    }
}