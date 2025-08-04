package org.pet.delivery.service;

import com.pet.common.client.OrderClient;
import com.pet.common.client.UpdateOrderStatus;
import com.pet.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pet.delivery.entity.Delivery;
import org.pet.delivery.entity.DeliveryStatus;
import org.pet.delivery.exception.DeliveryException;
import org.pet.delivery.kafka.AnalyticsProducer;
import org.pet.delivery.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final NotificationService notificationService;
    private final OrderClient orderClient;
    private final AnalyticsProducer analyticsProducer;

    public void createDeliveryForOrder(long orderId) {
        if (deliveryRepository.existsByOrderId(orderId)) {
            log.info("Delivery for order {} already exists. Skipping...", orderId);
            return;
        }

        Delivery delivery = new Delivery();
        delivery.setOrderId(orderId);
        delivery.setStatus(DeliveryStatus.CREATED);
        setSourceAndDestination(delivery);
        analyticsProducer.sendDeliveryUpdated(deliveryRepository.save(delivery));
    }

    public void cancelDeliveryForOrder(long orderId) {
        Delivery delivery = deliveryRepository.findByOrderId(orderId)
                .orElseGet(() -> {
                    Delivery newDelivery = new Delivery();
                    newDelivery.setOrderId(orderId);
                    setSourceAndDestination(newDelivery);
                    return new Delivery();
                });
        delivery.setStatus(DeliveryStatus.CANCELLED);
        analyticsProducer.sendDeliveryUpdated(deliveryRepository.save(delivery));

        notificationService.sendDeliveryCancelledNotification(delivery.getId(), delivery.getOrderId());
    }

    public void startDeliveryProcess(long deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new EntityNotFoundException("Delivery", deliveryId));
        if (DeliveryStatus.CANCELLED.equals(delivery.getStatus())) {
            log.info("Delivery for order {} cancelled. Skipping...", deliveryId);
            throw new DeliveryException("Delivery for order " + deliveryId + " cancelled");
        }

        delivery.setDeliveryDate(Instant.now());
        delivery.setStatus(DeliveryStatus.ACTIVE);
        analyticsProducer.sendDeliveryUpdated(deliveryRepository.save(delivery));

        CompletableFuture.runAsync(() -> orderClient.updateOrderStatus(delivery.getOrderId(), UpdateOrderStatus.IN_DELIVERY));
        notificationService.sendDeliveryPointChangedNotification(delivery.getId(), delivery.getOrderId());
    }

    private void setSourceAndDestination(Delivery delivery) {
        //TODO: fetch some data...
        delivery.setSource("Source");
        delivery.setDestination("Destination");
    }
}
