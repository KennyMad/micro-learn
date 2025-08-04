package com.pet.common.dto.response;

import java.math.BigDecimal;

public record OrderItemResponse(
        long itemId,
        int amount,
        BigDecimal price
) {
}
