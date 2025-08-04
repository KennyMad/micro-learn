package org.pet.analytics.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic deliveryAnalyticsTopic() {
        return TopicBuilder
                .name("delivery-analytics")
                .build();
    }

    @Bean
    public NewTopic orderAnalyticsTopic() {
        return TopicBuilder
                .name("order-analytics")
                .build();
    }

    @Bean
    public NewTopic paymentAnalyticsTopic() {
        return TopicBuilder
                .name("payment-analytics")
                .build();
    }
}
