package com.pet.common.client;

import java.util.List;

public record SynchronizeItemsRequest(
        List<SynchronizeItem> items
) {

}
