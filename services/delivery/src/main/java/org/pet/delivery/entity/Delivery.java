package org.pet.delivery.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private String source;
    @Column(nullable = false)
    private long orderId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    private Instant shipmentDate;
    private Instant deliveryDate;
}
