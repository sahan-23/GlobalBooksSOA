package com.yourcompany.shipping;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;

public class ShippingQueueConsumer {

    private final static String QUEUE_NAME = "shipping_queue";

    public void startListening() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        // IMPORTANT: Replace this with your own CloudAMQP URL
        String amqpUrl = "amqps://feijkknu:ZF0Wc_NkrkNBHi_AourTL_04mh_rcaEt@puffin.rmq2.cloudamqp.com/feijkknu";
        factory.setUri(amqpUrl);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages in " + QUEUE_NAME + ". To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
            // Add logic here to process the shipping order
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}