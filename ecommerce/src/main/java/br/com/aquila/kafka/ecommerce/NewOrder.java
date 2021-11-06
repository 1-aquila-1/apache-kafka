package br.com.aquila.kafka.ecommerce;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrder {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var dispatcher = new KafkaDispatcher()) {
            for (var i = 0; i < 10; i++) {

                var key = UUID.randomUUID().toString();
                var value = key + ",67523,1234";
                dispatcher.send(Topic.ECOMMERCE_NEW_ORDER.name(), key, value);

                var email = "Thank you for your order! We are processing your order!";
                dispatcher.send(Topic.ECOMMERCE_SEND_EMAIL.name(), key, email);
            }
        }
    }

}