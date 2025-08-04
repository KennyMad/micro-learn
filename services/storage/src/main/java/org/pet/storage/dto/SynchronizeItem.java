package org.pet.storage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record SynchronizeItem(
        long id,
        @NotNull(message = "Price is required")
        BigDecimal price,
        @Min(value = 0, message = "Amount must be positive")
        int amount
) {
}
