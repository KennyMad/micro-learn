package org.pet.storage.service;

import com.pet.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.pet.storage.dto.CreateItemCategoryRequest;
import org.pet.storage.entity.ItemCategory;
import org.pet.storage.repository.ItemCategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public long createCategory(CreateItemCategoryRequest request) {
        ItemCategory category = new ItemCategory();
        category.setName(request.name());
        return itemCategoryRepository.save(category).getId();
    }

    public ItemCategory getCategoryForName(String name) {
        return itemCategoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Item category", name));
    }

}
