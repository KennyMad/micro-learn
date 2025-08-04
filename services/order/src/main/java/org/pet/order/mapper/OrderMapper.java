package org.pet.order.mapper;

import org.pet.order.dto.request.CreateOrderRequest;
import org.pet.order.dto.response.OrderItemResponse;
import org.pet.order.dto.response.OrderResponse;
import org.pet.order.entity.Order;
import org.pet.order.entity.OrderItem;
import org.pet.order.entity.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }
        return new OrderResponse(
                order.getId(),
                order.getItems()
                        .stream()
                        .map(orderItem ->
                                new OrderItemResponse(orderItem.getItemId(), orderItem.getAmount(), orderItem.getPrice()))
                        .toList(),
                Date.from(order.getCreatedDate()),
                order.getStatus());
    }

    public Order toEntity(CreateOrderRequest createOrderRequest) {
        if (createOrderRequest == null) {
            return null;
        }
        Order order = new Order();
        order.setUserId(createOrderRequest.userId());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedDate(Instant.now());

        order.setItems(createOrderRequest.items().stream()
                .map(createItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setItemId(createItem.itemId());
                    orderItem.setAmount(createItem.amount());
                    orderItem.setPrice(createItem.price());
                    return orderItem;
                }).toList());

        return order;
    }
}
