package org.pet.delivery.kafka;

import com.pet.common.dto.kafka.OrderToDelivery;
import lombok.RequiredArgsConstructor;
import org.pet.delivery.service.DeliveryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryConsumer {

    private final DeliveryService deliveryService;

    @KafkaListener(topics = "delivery")
    public void consumeOrderToDelivery(OrderToDelivery orderToDelivery) {
        deliveryService.createDeliveryForOrder(orderToDelivery.orderId());
    }

    @KafkaListener(topics = "cancel-delivery")
    public void consumeCancelDelivery(OrderToDelivery orderToDelivery) {
        deliveryService.cancelDeliveryForOrder(orderToDelivery.orderId());
    }
}
