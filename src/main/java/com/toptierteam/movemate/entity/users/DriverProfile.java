package com.toptierteam.movemate.entity.users;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "driver_profiles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DriverProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String licenseNumber;

    private String vehicleType;
    private String vehicleModel;
    private String vehiclePlateNumber;
    private String vehicleColor;
    private Integer vehicleYear;

    @Builder.Default
    private boolean isVerified = false;
    @Builder.Default
    private boolean isActive = true;

    @Builder.Default
    private Double rating = 0.0;
    @Builder.Default
    private Integer totalTrips = 0;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
