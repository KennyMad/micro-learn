package org.pet.storage.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pet.storage.dto.CreateItemCategoryRequest;
import org.pet.storage.dto.CreateItemRequest;
import org.pet.storage.dto.SynchronizeItemsRequest;
import org.pet.storage.model.ValidationResult;
import org.pet.storage.service.ItemCategoryService;
import org.pet.storage.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/item")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;
    private final ItemCategoryService itemCategoryService;

    @PostMapping("/category")
    public ResponseEntity<?> createItemCategory(@RequestBody @Valid CreateItemCategoryRequest request) {
        return ResponseEntity.ok(itemCategoryService.createCategory(request));
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody @Valid CreateItemRequest request) {
        return ResponseEntity.ok(itemService.createItem(request));
    }

    @PostMapping("/synchronize")
    public ResponseEntity<?> synchronizeItems(@RequestBody @Valid SynchronizeItemsRequest request) {
        ValidationResult result = itemService.synchronizeItems(request);
        return result.isValid()
                ? ResponseEntity.ok().build()
                : ResponseEntity.badRequest().body(result.getError());
    }
}
