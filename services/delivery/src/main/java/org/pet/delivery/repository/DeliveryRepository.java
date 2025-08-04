package org.pet.delivery.repository;

import org.pet.delivery.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    boolean existsByOrderId(long orderId);

    Optional<Delivery> findByOrderId(long orderId);
}
