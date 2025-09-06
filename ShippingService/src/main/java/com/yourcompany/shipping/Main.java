package com.yourcompany.shipping;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Shipping Service Consumer ---");
        ShippingQueueConsumer consumer = new ShippingQueueConsumer();
        try {
            consumer.startListening();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}