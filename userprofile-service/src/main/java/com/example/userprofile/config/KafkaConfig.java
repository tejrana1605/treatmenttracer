package com.example.userprofile.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

// https://docs.spring.io/spring-kafka/reference/kafka/configuring-topics.html
@Configuration
public class KafkaConfig {

    // With spring boot KafkaAdmin bean is created automatically
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> map = new HashMap<>();
        map.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new KafkaAdmin(map);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder
                .name("user-registration-events")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
