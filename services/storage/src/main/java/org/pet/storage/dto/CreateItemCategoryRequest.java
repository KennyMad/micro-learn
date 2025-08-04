package org.pet.storage.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateItemCategoryRequest(
        @NotEmpty(message = "Category name is required")
        String name
) {
}
