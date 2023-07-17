package com.example.producer.demo;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SecondService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final HashMap<Integer, String> hashMap;

    private int numWordsToSend = 10;

    @Autowired
    public SecondService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.hashMap = new HashMap<>();
        this.hashMap.put(1, "Java");
        this.hashMap.put(2, "Kafka");
        this.hashMap.put(3, "Messaging");
        this.hashMap.put(4, "Service");
    }

    @KafkaListener(topics = "topic1")
    public void listenMessage(String message) {
        if (numWordsToSend <= 0) {
            return;
        }

        int randomKey = getRandomKey();
        String reply = hashMap.get(randomKey) + " " + message;
        kafkaTemplate.send("topic2", reply);
        System.out.println("Second service received message: " + message + ", replied with: " + reply);

        numWordsToSend--;
    }

    private int getRandomKey() {
        return (int) (Math.random() * hashMap.size()) + 1;
    }
}
