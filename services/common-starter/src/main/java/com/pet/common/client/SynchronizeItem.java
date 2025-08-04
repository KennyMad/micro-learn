package com.pet.common.client;

import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record SynchronizeItem(
        long id,
        BigDecimal price,
        int amount
) {
}
