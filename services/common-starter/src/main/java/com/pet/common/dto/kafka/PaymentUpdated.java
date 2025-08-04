package com.pet.common.dto.kafka;

import java.time.Instant;

public record PaymentUpdated(
        long paymentId,
        Instant updateTime,
        String paymentStatus
) {
}
