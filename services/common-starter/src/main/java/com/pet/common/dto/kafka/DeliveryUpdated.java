package com.pet.common.dto.kafka;

import java.time.Instant;

public record DeliveryUpdated(
        long deliveryId,
        Instant updateTime,
        String deliveryStatus
) {
}
