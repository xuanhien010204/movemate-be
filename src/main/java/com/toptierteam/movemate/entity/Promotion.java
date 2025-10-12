package com.toptierteam.movemate.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String promotionType; // PERCENTAGE, FIXED_AMOUNT, FREE_DELIVERY
    private BigDecimal discountValue;
    private BigDecimal maxDiscountAmount;
    private BigDecimal minOrderAmount;

    private Integer maxUsageCount;
    @Builder.Default
    private Integer currentUsageCount = 0;
    @Builder.Default
    private Integer maxUsagePerUser = 1;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder.Default
    private boolean isActive = true;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
