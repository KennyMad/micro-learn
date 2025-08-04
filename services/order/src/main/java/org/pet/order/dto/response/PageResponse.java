package org.pet.order.dto.response;

import java.util.List;

public record PageResponse<T>(
        List<T> results,
        int page,
        int total
) {
}
