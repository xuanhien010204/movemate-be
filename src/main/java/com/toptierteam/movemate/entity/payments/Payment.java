package com.toptierteam.movemate.entity.payments;

import com.toptierteam.movemate.entity.trips.Trip;
import com.toptierteam.movemate.entity.users.User;
import com.toptierteam.movemate.enums.MethodPayment;
import com.toptierteam.movemate.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_id")
    private User payer;

    @Column(unique = true, nullable = false)
    private String paymentId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private MethodPayment paymentMethod;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDING;

    private String description;
    private String externalPaymentId; // From payment gateway
    private String gatewayResponse;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime paidAt;
    private LocalDateTime refundedAt;
}
