package org.pet.payment.kafka;

import com.pet.common.dto.kafka.PaymentUpdated;
import lombok.RequiredArgsConstructor;
import org.pet.payment.entity.Payment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AnalyticsProducer {

    private final KafkaTemplate<String, PaymentUpdated> kafkaTemplate;

    @Async
    public void sendPaymentUpdated(Payment payment) {
        kafkaTemplate.send("payment-analytics", new PaymentUpdated(payment.getId(), Instant.now(), payment.getStatus().name()));
    }
}
