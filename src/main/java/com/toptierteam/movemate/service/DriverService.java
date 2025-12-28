package com.toptierteam.movemate.service;

import com.toptierteam.movemate.dto.request.DriverRegisterRequest;
import com.toptierteam.movemate.dto.request.DriverStatusUpdateRequest;
import com.toptierteam.movemate.dto.request.DriverVerificationRequest;
import com.toptierteam.movemate.dto.response.DriverProfileResponse;
import com.toptierteam.movemate.dto.response.MessageResponse;

import java.util.List;

public interface DriverService {

    /**
     * register new driver
     */
    MessageResponse registerDriver(DriverRegisterRequest request);

    /**
     * Admin verify driver (approve or reject)
     */
    DriverProfileResponse verifyDriver(Long driverId, DriverVerificationRequest request, Long adminId);

    /**
     * Get list driver available (ONLINE)
     */
    List<DriverProfileResponse> getAvailableDrivers();

    /**
     * Get list nearby drivers by location
     */
    List<DriverProfileResponse> getNearbyDrivers(Double latitude, Double longitude, int limit);

    /**
     * Get list approved drivers (APPROVED)
     */
    List<DriverProfileResponse> getPendingDrivers();

    /**
     * Get driver profile by driver ID
     */
    DriverProfileResponse getDriverProfile(Long driverId);

    /**
     * Get driver profile by user ID
     */
    DriverProfileResponse getDriverProfileByUserId(Long userId);

    /**
     * Update driver status (ONLINE, OFFLINE, BUSY)
     */
    DriverProfileResponse updateDriverStatus(Long userId, DriverStatusUpdateRequest request);

    /**
     * Check if driver can accept new trip
     */
    boolean canAcceptTrip(Long driverId);
}

