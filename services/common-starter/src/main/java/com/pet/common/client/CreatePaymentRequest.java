package com.pet.common.client;

import java.math.BigDecimal;

public record CreatePaymentRequest(
    long orderId,
    BigDecimal total
) {
}
