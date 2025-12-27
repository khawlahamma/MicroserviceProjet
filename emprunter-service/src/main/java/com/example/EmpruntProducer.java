
package com.example;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EmpruntProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public EmpruntProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String message) {
        kafkaTemplate.send("emprunt-created", message);
    }
}
