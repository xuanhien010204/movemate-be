package com.toptierteam.movemate.repository;

import com.toptierteam.movemate.entity.users.DriverProfile;
import com.toptierteam.movemate.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverProfileRepository extends JpaRepository<DriverProfile, Long> {
    Optional<DriverProfile> findByUserId(Long userId);

    Optional<DriverProfile> findByLicenseNumber(String licenseNumber);

    Optional<DriverProfile> findByVehiclePlateNumber(String vehiclePlateNumber);

    Boolean existsByLicenseNumber(String licenseNumber);

    Boolean existsByVehiclePlateNumber(String vehiclePlateNumber);

    // Tìm tài xế theo trạng thái và đã được xác thực
    List<DriverProfile> findByStatusAndIsVerifiedAndIsActive(
        DriverStatus status,
        boolean isVerified,
        boolean isActive
    );

    // Tìm tài xế sẵn sàng (online và không bận)
    @Query("SELECT d FROM DriverProfile d WHERE d.status IN ('ONLINE') " +
           "AND d.isVerified = true AND d.isActive = true")
    List<DriverProfile> findAvailableDrivers();

    // Tìm tài xế gần nhất với vị trí (sử dụng Haversine formula)
    @Query(value = "SELECT * FROM driver_profiles d " +
           "WHERE d.is_verified = true AND d.is_active = true " +
           "AND d.status = 'ONLINE' " +
           "AND d.current_latitude IS NOT NULL AND d.current_longitude IS NOT NULL " +
           "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(d.current_latitude)) " +
           "* cos(radians(d.current_longitude) - radians(:longitude)) " +
           "+ sin(radians(:latitude)) * sin(radians(d.current_latitude)))) " +
           "LIMIT :limit", nativeQuery = true)
    List<DriverProfile> findNearbyDrivers(Double latitude, Double longitude, int limit);

    // Tìm tài xế chờ xác thực
    List<DriverProfile> findByIsVerifiedAndIsActive(boolean isVerified, boolean isActive);
}

