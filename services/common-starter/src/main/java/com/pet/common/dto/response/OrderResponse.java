package com.pet.common.dto.response;

import java.util.Date;
import java.util.List;

public record OrderResponse(
        long id,
        List<OrderItemResponse> items,
        Date createdDate,
        String status
) {
}
