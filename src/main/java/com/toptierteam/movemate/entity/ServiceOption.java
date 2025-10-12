package com.toptierteam.movemate.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "service_options")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ServiceOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceName;

    private String description;
    private BigDecimal basePrice;
    private String unit; // per hour, per km, per item

    @Builder.Default
    private boolean isActive = true;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
