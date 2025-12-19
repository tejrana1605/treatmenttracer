package com.example.authservice.service;

import com.example.authservice.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


@Component
public class UserDetialsConsumerService {

    private final InternalCredentialsService service;

    public UserDetialsConsumerService(InternalCredentialsService service) {
        this.service = service;
    }
    private final Logger logger = LoggerFactory.getLogger(UserDetialsConsumerService.class.getName());

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "auth-service", concurrency = "3")
    public void userDetialsConsumer(UserRegisteredEvent request,@Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                                    @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            logger.info("User details received: {}, Patition: {}, Offset: {}, Thread: {} ", request, partition, offset, Thread.currentThread().getName());
            service.createCredential(request);

        } catch (Exception e) {
            logger.error("Error while consuming message: {}", e.getMessage());
        }
    }

}