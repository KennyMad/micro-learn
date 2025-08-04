package org.pet.analytics.kafka;

import com.pet.common.dto.kafka.DeliveryUpdated;
import com.pet.common.dto.kafka.OrderUpdated;
import com.pet.common.dto.kafka.PaymentUpdated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsConsumer {

    @KafkaListener(topics = {"delivery-analytics"})
    public void consumeDeliveryAnalytics(DeliveryUpdated deliveryUpdated) {
        log.info("Received delivery updated : {}", deliveryUpdated);
    }

    @KafkaListener(topics = {"order-analytics"})
    public void consumeOrderAnalytics(OrderUpdated orderUpdated) {
        log.info("Received order updated : {}", orderUpdated);
    }

    @KafkaListener(topics = {"payment-analytics"})
    public void consumePaymentAnalytics(PaymentUpdated paymentUpdated) {
        log.info("Received payment updated : {}", paymentUpdated);
    }
}
