package org.pet.order.resource;

import com.pet.common.TestUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.pet.order.dto.request.CreateOrderRequest;
import org.pet.order.entity.OrderStatus;
import org.pet.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable long id, @TestUser long userId) {
        orderService.cancelOrder(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable long id, @RequestBody @Valid OrderStatus status, @TestUser long userId) {
        orderService.updateOrderStatus(id, status, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable long id, @TestUser long userId) {
        return ResponseEntity.ok(orderService.getSingleOrder(id, userId));
    }

    @GetMapping("/history")
    public ResponseEntity<?> getOrderHistory(@RequestParam Date from, @RequestParam(required = false) Date to, @RequestParam(required = false) int page, @RequestParam(required = false) int size, @TestUser long userId) {
        return ResponseEntity.ok(orderService.getOrderHistory(from, to, page, size, userId));
    }
}
