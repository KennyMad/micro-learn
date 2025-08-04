package org.pet.storage.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateItemRequest(
        @NotEmpty(message = "Item name is required")
        String name,
        String description,
        @NotNull(message = "Item price is required")
        @Min(0)
        BigDecimal price,
        @Min(0)
        long quantity,
        @NotEmpty(message = "Item category is required")
        String category
) {
}
