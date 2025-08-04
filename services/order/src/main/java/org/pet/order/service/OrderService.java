package org.pet.order.service;

import com.pet.common.client.*;
import com.pet.common.dto.kafka.OrderToDelivery;
import com.pet.common.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.pet.order.dto.request.CreateOrderRequest;
import org.pet.order.dto.response.OrderResponse;
import org.pet.order.dto.response.PageResponse;
import org.pet.order.entity.Order;
import org.pet.order.entity.OrderItem;
import org.pet.order.entity.OrderStatus;
import org.pet.order.exception.IllegalOrderOperationException;
import org.pet.order.kafka.AnalyticsProducer;
import org.pet.order.kafka.OrderDeliveryProducer;
import org.pet.order.mapper.OrderMapper;
import org.pet.order.repository.OrderItemRepository;
import org.pet.order.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final TransactionalExecutionService transactionalExecutionService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final StorageClient storageClient;
    private final PaymentClient paymentClient;
    private final OrderDeliveryProducer orderDeliveryProducer;
    private final AnalyticsProducer analyticsProducer;

    public long createOrder(CreateOrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);
        validateItemsAvailability(order.getItems());
        transactionalExecutionService.executeInTransaction(() -> {
            orderRepository.save(order);
            orderItemRepository.saveAll(order.getItems());
        });

        createPayment(order);
        analyticsProducer.sendOrderUpdated(order);
        return order.getId();
    }

    public void updateOrderStatus(long id, OrderStatus status, Long userId) {
        if (statusStrategies.containsKey(status)) {
            statusStrategies.get(status).accept(id, userId);
        }
    }
    private final Map<OrderStatus, BiConsumer<Long, Long>> statusStrategies = Map.of(
            OrderStatus.CANCELLED, this::cancelOrder,
            OrderStatus.PENDING, this::prepareOrderForDelivery,
            OrderStatus.IN_DELIVERY, this::setOrderInDelivery);

    public void cancelOrder(long id, long userId) {
        Order order = getOrderByIdAndUserId(id, userId);
        if (OrderStatus.IN_DELIVERY.equals(order.getStatus()) || OrderStatus.COMPLETED.equals(order.getStatus())) {
            throw new IllegalOrderOperationException("Cannot cancel delivery order due to the current status of " + order.getStatus());
        }
        orderDeliveryProducer.sendOrderToDeliveryCancel(new OrderToDelivery(order.getId()));
        order.setStatus(OrderStatus.CANCELLED);
        analyticsProducer.sendOrderUpdated(orderRepository.save(order));
    }

    private void prepareOrderForDelivery(long id, long userId) {
        Order order = getOrderByIdAndUserId(id, userId);
        order.setStatus(OrderStatus.PENDING);
        analyticsProducer.sendOrderUpdated(orderRepository.save(order));
        orderDeliveryProducer.sendOrderToDelivery(new OrderToDelivery(order.getId()));
    }

    private void setOrderInDelivery(long id, long userId) {
        Order order = getOrderByIdAndUserId(id, userId);
        order.setStatus(OrderStatus.IN_DELIVERY);
        analyticsProducer.sendOrderUpdated(orderRepository.save(order));
    }

    public PageResponse<OrderResponse> getOrderHistory(Date from, Date to, int page, int size, Long userId) {
        Pageable pageable = PageRequest.of(page, size == 0 ? 10 : size);
        Page<Order> orderPage = to == null
                ? orderRepository.findOrdersByUserIdAndCreatedDateAfter(userId, from.toInstant(), pageable)
                : orderRepository.findOrdersByUserIdAndCreatedDateAfterAndCreatedDateBefore(userId, from.toInstant(), to.toInstant(), pageable);

        return new PageResponse<>(orderPage.stream().map(order -> transactionalExecutionService.executeInTransaction(() -> orderMapper.toResponse(order))).toList(), page, orderPage.getTotalPages());
    }

    public OrderResponse getSingleOrder(long id, Long userId) {
        Order order = getOrderByIdAndUserId(id, userId);
        return transactionalExecutionService.executeInTransaction(() -> orderMapper.toResponse(order));
    }

    private void validateItemsAvailability(List<OrderItem> items) {
        storageClient.synchronizeItems(new SynchronizeItemsRequest(items.stream().map(item -> new SynchronizeItem(item.getItemId(), item.getPrice(), item.getAmount())).toList()));
    }

    private void createPayment(Order order) {
        BigDecimal totalAmount = order.getItems().stream().map(orderItem -> orderItem.getPrice().multiply(new BigDecimal(orderItem.getAmount()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        CompletableFuture.supplyAsync(() ->
            paymentClient.createPayment(new CreatePaymentRequest(order.getId(), totalAmount))
        ).thenRunAsync(() -> {
            order.setStatus(OrderStatus.WAITING_FOR_PAYMENT);
            orderRepository.save(order);
        });
    }

    private Order getOrderByIdAndUserId(long id, long userId) {
        return orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order", "id: " + id + ", userId: " + userId));
    }
}
