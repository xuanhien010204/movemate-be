package com.toptierteam.movemate.entity.trips;

import com.toptierteam.movemate.entity.users.User;
import com.toptierteam.movemate.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trips")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private User driver;

    // Pickup location
    private String pickupAddress;
    private Double pickupLatitude;
    private Double pickupLongitude;

    // Destination location
    private String destinationAddress;
    private Double destinationLatitude;
    private Double destinationLongitude;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.PENDING;

    private BigDecimal estimatedPrice;
    private BigDecimal finalPrice;
    private Double estimatedDistance; // in km
    private Integer estimatedDuration; // in minutes

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime scheduledAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TripTracking> trackings = new ArrayList<>();

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    @Builder.Default
    private List<TripService> services = new ArrayList<>();
}
