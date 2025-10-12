package com.toptierteam.movemate.entity.trips;

import com.toptierteam.movemate.entity.ServiceOption;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "trip_services")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TripService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_option_id")
    private ServiceOption serviceOption;

    @Builder.Default
    private Integer quantity = 1;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}
