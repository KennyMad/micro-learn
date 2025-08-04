package com.pet.common.client;

import com.pet.common.dto.response.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8010/api/v1/order", name = "Order")
public interface OrderClient {

    @PutMapping("/{orderId}/cancel")
    void cancelOrder(@PathVariable long orderId);

    @PutMapping("/{orderId}/status")
    void updateOrderStatus(@PathVariable long orderId, @RequestBody UpdateOrderStatus orderStatus);

    @GetMapping("/{orderId}")
    OrderResponse getOrder(@PathVariable long orderId, long userId);
}
