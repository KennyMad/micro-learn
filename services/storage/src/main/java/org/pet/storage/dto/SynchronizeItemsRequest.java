package org.pet.storage.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SynchronizeItemsRequest(
        @NotEmpty(message = "At least one item is required")
        List<SynchronizeItem> items
) {

}
