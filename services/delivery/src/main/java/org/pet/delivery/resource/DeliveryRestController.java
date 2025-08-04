package org.pet.delivery.resource;

import lombok.RequiredArgsConstructor;
import org.pet.delivery.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryRestController {

    private final DeliveryService deliveryService;

    @PostMapping("/{deliveryId}/start")
    public ResponseEntity<?> startDelivery(@PathVariable Long deliveryId) {
        deliveryService.startDeliveryProcess(deliveryId);
        return ResponseEntity.ok().build();
    }
}
