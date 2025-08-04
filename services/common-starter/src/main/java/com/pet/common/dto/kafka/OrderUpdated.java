package com.pet.common.dto.kafka;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderUpdated(
        long orderId,
        Instant updateTime,
        BigDecimal orderTotal,
        String orderStatus
) {
}
