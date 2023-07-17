package com.example.consumer.demo;

import java.util.HashMap;
import java.util.Map;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class FirstService {

    private static final String TOPIC = "my-topic";

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Map<String, String> words = new HashMap<>() {{
        put("second", "World");
        put("third", "From");
        put("fourth", "Kafka");
    }};

    public FirstService(KafkaTemplate<String, String>kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = TOPIC)
    public void receiveMessage(String message) {
        String word = words.get("second");
        String newMessage = message + " " + word;

        kafkaTemplate.send(TOPIC, newMessage);
    }
}
