package org.pet.payment.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "amount")
    private BigDecimal total;
    @Column(nullable = false)
    private long orderId;
    @Column(nullable = false)
    @CreatedDate
    private Instant createdDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
