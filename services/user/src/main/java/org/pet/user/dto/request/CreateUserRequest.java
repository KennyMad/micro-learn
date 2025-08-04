package org.pet.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record CreateUserRequest(
        @NotEmpty(message = "Fullname is required")
        String fullname,
        @NotEmpty(message = "City is required")
        String city,
        @Email
        @NotEmpty(message = "Email is required")
        String email,
        @NotNull(message = "Birthday is required")
        Date birthday
) {
}
