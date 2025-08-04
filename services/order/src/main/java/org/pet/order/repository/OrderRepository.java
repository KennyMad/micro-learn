package org.pet.order.repository;

import org.pet.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUserId(long orderId, long userId);

    Page<Order> findOrdersByUserIdAndCreatedDateAfter(long userId, Instant createdDate, Pageable pageable);

    Page<Order> findOrdersByUserIdAndCreatedDateAfterAndCreatedDateBefore(long userId, Instant afterCreatedDate, Instant beforeCreatedDate, Pageable pageable);
}
