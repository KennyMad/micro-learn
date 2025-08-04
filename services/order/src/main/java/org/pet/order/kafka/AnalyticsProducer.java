package org.pet.order.kafka;

import com.pet.common.dto.kafka.OrderUpdated;
import lombok.RequiredArgsConstructor;
import org.pet.order.entity.Order;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AnalyticsProducer {

    private final KafkaTemplate<String, OrderUpdated> kafkaTemplate;

    @Async
    public void sendOrderUpdated(Order order) {
        BigDecimal totalAmount = order.getItems().stream().map(orderItem -> orderItem.getPrice().multiply(new BigDecimal(orderItem.getAmount()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        kafkaTemplate.send("order-analytics", new OrderUpdated(order.getId(), Instant.now(), totalAmount, order.getStatus().name()));
    }
}
