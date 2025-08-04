package org.pet.order.dto.response;

import org.pet.order.entity.OrderStatus;

import java.util.Date;
import java.util.List;

public record OrderResponse(
        long id,
        List<OrderItemResponse> items,
        Date createdDate,
        OrderStatus status
) {
}
