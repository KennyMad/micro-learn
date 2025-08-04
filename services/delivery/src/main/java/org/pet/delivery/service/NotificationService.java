package org.pet.delivery.service;

import com.pet.common.client.NotificationClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationClient notificationClient;

    @Async
    public void sendDeliveryCancelledNotification(long deliveryId, long orderId) {
        sendDeliveryNotification(deliveryId, orderId, "Delivery cancelled");
    }

    @Async
    public void sendDeliveryPointChangedNotification(long deliveryId, long orderId) {
        //todo: some point change
        sendDeliveryNotification(deliveryId, orderId, "Delivery point changed");
    }

    private void sendDeliveryNotification(long deliveryId, long orderId, String message) {
        notificationClient.sendNotification(message + "\n For deliveryId: " + deliveryId + "\n OrderId: " + orderId);
    }
}
