package com.example.userprofile.service;

import com.example.userprofile.event.UserRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@Component
public class MessagePublisher {

    private final KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate;
    private final Logger logger = Logger.getLogger(MessagePublisher.class.getName());

    public MessagePublisher(KafkaTemplate<String, UserRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(String topic, UserRegisteredEvent payload) {
        logger.info("Publishing message to topic: " + topic);
        CompletableFuture<SendResult<String, UserRegisteredEvent>> result = kafkaTemplate.send(topic, payload);
        result.whenComplete((r, e) -> {
            if (e == null) {
                logger.info("Message published to topic: " + topic);
            } else {
                logger.severe("Error while publishing message to topic: " + topic);
                logger.severe(e.getMessage());
            }
        });
    }
}
