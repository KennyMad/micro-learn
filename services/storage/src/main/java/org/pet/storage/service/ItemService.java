package org.pet.storage.service;

import brave.Tracer;
import com.pet.common.dto.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pet.storage.dto.CreateItemRequest;
import org.pet.storage.dto.SynchronizeItem;
import org.pet.storage.dto.SynchronizeItemsRequest;
import org.pet.storage.entity.Item;
import org.pet.storage.mapper.ItemMapper;
import org.pet.storage.model.ValidationResult;
import org.pet.storage.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ItemCategoryService itemCategoryService;

    public long createItem(CreateItemRequest request) {
        Item newItem = itemMapper.toEntity(request);
        newItem.setCategory(itemCategoryService.getCategoryForName(request.category()));
        return itemRepository.save(newItem).getId();
    }

    public ValidationResult synchronizeItems(SynchronizeItemsRequest request) {
        ValidationResult result = new ValidationResult();
        result.setValid(true);
        Map<String, String> errors = new HashMap<>();
        result.setError(new ErrorResponse(errors));
        List<Item> items = itemRepository.findAllById(request.items().stream().map(SynchronizeItem::id).toList());

        Map<Long, Item> itemMap = items.stream().collect(Collectors.toMap(Item::getId, item -> item));
        for (SynchronizeItem item : request.items()) {
            Item existingItem = itemMap.get(item.id());
            StringBuilder errorBuilder = new StringBuilder();
            if (existingItem == null) {
                errorBuilder.append("Item not found");
            } else {
                if (!existingItem.getPrice().equals(item.price())) {
                    errorBuilder.append("Item price is not valid. ");
                }
                if (existingItem.getQuantity() < item.amount()) {
                    errorBuilder.append("Quantity is insufficient. ");
                }
            }
            if (!errorBuilder.isEmpty()) {
                result.setValid(false);
                errors.put(String.valueOf(item.id()), errorBuilder.toString());
            }
        }
        return result;
    }
}
