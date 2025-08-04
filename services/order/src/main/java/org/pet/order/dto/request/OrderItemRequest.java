package org.pet.order.dto.request;

import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

@Validated
public record OrderItemRequest(
        long itemId,
        @Min(value = 1, message = "At least one item is required") int amount,
        @Min(value = 0, message = "Price must be positive number") BigDecimal price
) {}
