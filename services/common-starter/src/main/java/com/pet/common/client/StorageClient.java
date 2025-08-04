package com.pet.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8020/api/v1/item", name = "storage")
public interface StorageClient {

    @PostMapping("/synchronize")
    void synchronizeItems(@RequestBody SynchronizeItemsRequest request);
}
