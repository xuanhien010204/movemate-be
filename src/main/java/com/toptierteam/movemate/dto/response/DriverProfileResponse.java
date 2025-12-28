package com.toptierteam.movemate.dto.response;

import com.toptierteam.movemate.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverProfileResponse {

    private Long id;
    private Long userId;
    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;

    // License Information
    private String licenseNumber;
    private String licenseType;
    private LocalDateTime licenseExpiry;

    // Vehicle Information
    private String vehicleType;
    private String vehicleModel;
    private String vehiclePlateNumber;
    private String vehicleColor;
    private Integer vehicleYear;
    private Double vehicleCapacity;

    // Status
    private DriverStatus status;
    private boolean isVerified;
    private boolean isActive;
    private String verificationNote;
    private LocalDateTime verifiedAt;

    // Statistics
    private Double rating;
    private Integer totalTrips;
    private Integer completedTrips;
    private Integer cancelledTrips;

    // Location
    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime lastLocationUpdate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

