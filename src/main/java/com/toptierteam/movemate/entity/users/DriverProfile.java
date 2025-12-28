package com.toptierteam.movemate.entity.users;

import com.toptierteam.movemate.enums.DriverStatus;
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
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private String licenseType; // B1, B2, C, D, E...

    @Column(nullable = false)
    private LocalDateTime licenseExpiry;

    // Vehicle Information
    @Column(nullable = false)
    private String vehicleType; // Xe tải nhỏ, Xe tải lớn, Xe ba gác...

    private String vehicleModel;

    @Column(nullable = false, unique = true)
    private String vehiclePlateNumber;

    private String vehicleColor;
    private Integer vehicleYear;

    @Column(nullable = false)
    private Double vehicleCapacity; // Trọng tải (tấn)

    // Identity Documents
    private String identityCardNumber;
    private String identityCardFrontUrl;
    private String identityCardBackUrl;
    private String licenseFrontUrl;
    private String licenseBackUrl;
    private String vehicleRegistrationUrl;
    private String insuranceUrl;

    // Driver Status
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DriverStatus status = DriverStatus.OFFLINE;

    @Builder.Default
    private boolean isVerified = false;

    @Builder.Default
    private boolean isActive = true;

    private String verificationNote; // Ghi chú khi duyệt/từ chối
    private LocalDateTime verifiedAt;
    private Long verifiedBy; // Admin ID who verified

    // Statistics
    @Builder.Default
    private Double rating = 5.0;

    @Builder.Default
    private Integer totalTrips = 0;

    @Builder.Default
    private Integer completedTrips = 0;

    @Builder.Default
    private Integer cancelledTrips = 0;

    // Location tracking
    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime lastLocationUpdate;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
