package com.pet.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8070/api/v1/notification", name = "Notification")
public interface NotificationClient {

    @PostMapping
    void sendNotification(@RequestBody String message);
}
