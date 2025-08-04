package org.pet.delivery.kafka;

import com.pet.common.dto.kafka.DeliveryUpdated;
import lombok.RequiredArgsConstructor;
import org.pet.delivery.entity.Delivery;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AnalyticsProducer {

    private final KafkaTemplate<String, DeliveryUpdated> kafkaTemplate;

    @Async
    public void sendDeliveryUpdated(Delivery delivery) {
        kafkaTemplate.send("delivery-analytics", new DeliveryUpdated(delivery.getId(), Instant.now(), delivery.getStatus().name()));
    }
}
