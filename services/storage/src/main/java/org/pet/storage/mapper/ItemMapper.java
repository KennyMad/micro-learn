package org.pet.storage.mapper;

import org.pet.storage.dto.CreateItemRequest;
import org.pet.storage.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public Item toEntity(CreateItemRequest request) {
        Item item = new Item();
        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setQuantity(request.quantity());
        return item;
    }
}
