package com.toptierteam.movemate.entity;

import com.toptierteam.movemate.entity.users.User;
import com.toptierteam.movemate.enums.MethodPayment;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_logs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true, nullable = false)
    private String transactionId;

    private String transactionType; // PAYMENT, REFUND, DEPOSIT, WITHDRAWAL

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private MethodPayment paymentMethod;

    private String status; // PENDING, SUCCESS, FAILED, CANCELLED
    private String description;
    private String referenceId; // Trip ID, Order ID, etc.

    private String externalTransactionId; // From payment gateway
    private String gatewayResponse;

    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
