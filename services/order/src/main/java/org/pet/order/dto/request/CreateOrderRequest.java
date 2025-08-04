package org.pet.order.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty(message = "At least one item is required")
        List<OrderItemRequest> items,
        @NotNull(message = "User id is required")
        long userId
) {
}
