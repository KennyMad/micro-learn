package org.pet.delivery.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaDeliveryTopicConfig {

    @Bean
    public NewTopic deliveryTopic() {
        return TopicBuilder.name("delivery").build();
    }

    @Bean
    public NewTopic cancelDeliveryTopic() {
        return TopicBuilder.name("cancel-delivery").build();
    }
}
