package com.yourcompany.payments;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import java.nio.charset.StandardCharsets;

public class ShippingQueueProducer {

    // අපි පණිවිඩ යවන queue එකේ නම
    private final static String QUEUE_NAME = "shipping_queue";

    public void sendMessage(String message) throws Exception {

        // 1. Connection Factory එක හදාගන්න
        ConnectionFactory factory = new ConnectionFactory();

        // 2. ඔබගේ CloudAMQP සම්බන්ධතා URL එක මෙතනට යොදන්න
        // උදා: "amqps://username:password@hostname/vhost"
        String amqpUrl = "amqps://feijkknu:ZF0Wc_NkrkNBHi_AourTL_04mh_rcaEt@puffin.rmq2.cloudamqp.com/feijkknu";

        factory.setUri(amqpUrl);

        // Try-with-resources මගින් connection සහ channel ස්වයංක්‍රීයව වැසී යයි
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // 3. Queue එක නිර්වචනය කරන්න
            // durable=true ලෙස සැකසීමෙන් server එක restart කළත් queue එක මැකී යන්නේ නැත
            boolean durable = true;
            channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

            // 4. පණිවිඩය Queue එකට යවන්න (Publish)
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));

            System.out.println(" [x] '" + message + "' යන පණිවිඩය shipping_queue වෙත යවන ලදී.");
        }
    }
}