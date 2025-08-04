package org.pet.user.dto.request;

import java.math.BigDecimal;
import java.util.Date;

public record UserResponse(
        Long id,
        String fullname,
        String city,
        String email,
        Date birthday,
        BigDecimal balance
) {
}
