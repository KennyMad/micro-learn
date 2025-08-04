package org.pet.notification.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationRestController {

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody String notification) {

        log.info("Received notification: {}", notification);
        //Some logic
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

        return ResponseEntity.ok().build();
    }
}
