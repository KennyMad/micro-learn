package com.pet.common.dto.request;

import java.math.BigDecimal;

public record WithdrawRequest(
        BigDecimal amount,
        long orderId
) {
}
