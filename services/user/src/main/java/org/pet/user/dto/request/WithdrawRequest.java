package org.pet.user.dto.request;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;

public record WithdrawRequest(
        @Min(value = 0, message = "Amount must be positive") BigDecimal amount,
        long orderId
) {
}
