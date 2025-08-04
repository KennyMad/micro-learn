package org.pet.order.kafka;

import com.pet.common.dto.kafka.OrderToDelivery;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderDeliveryProducer {

    private final KafkaTemplate<String, OrderToDelivery> kafkaTemplate;

    public void sendOrderToDelivery(OrderToDelivery orderToDelivery) {
        Message<OrderToDelivery> message = MessageBuilder
                .withPayload(orderToDelivery)
                .setHeader(KafkaHeaders.TOPIC, "delivery")
                .build();

        kafkaTemplate.send(message);
    }

    public void sendOrderToDeliveryCancel(OrderToDelivery orderToDelivery) {
        kafkaTemplate.send("cancel-delivery", orderToDelivery);
    }
}
