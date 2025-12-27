
package com.example;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @KafkaListener(topics = "emprunt-created", groupId = "notification-group")
    public void consume(String msg) {
        System.out.println("NOTIFICATION : " + msg);
    }
}
