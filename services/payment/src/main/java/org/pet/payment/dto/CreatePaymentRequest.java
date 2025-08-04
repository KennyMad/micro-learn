package org.pet.payment.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatePaymentRequest(
    long orderId,
    @NotNull
    @Min(0)
    BigDecimal total
) {
}
