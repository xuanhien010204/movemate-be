package com.toptierteam.movemate.service.impl;

import com.toptierteam.movemate.dto.request.DriverRegisterRequest;
import com.toptierteam.movemate.dto.request.DriverStatusUpdateRequest;
import com.toptierteam.movemate.dto.request.DriverVerificationRequest;
import com.toptierteam.movemate.dto.response.DriverProfileResponse;
import com.toptierteam.movemate.dto.response.MessageResponse;
import com.toptierteam.movemate.entity.users.DriverProfile;
import com.toptierteam.movemate.entity.users.User;
import com.toptierteam.movemate.enums.DriverStatus;
import com.toptierteam.movemate.enums.RoleType;
import com.toptierteam.movemate.repository.DriverProfileRepository;
import com.toptierteam.movemate.repository.UserRepository;
import com.toptierteam.movemate.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverServiceImpl implements DriverService {

    private final UserRepository userRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MessageResponse registerDriver(DriverRegisterRequest request) {
        // check username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username đã tồn tại!");
        }

        // check email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // check license number exists
        if (driverProfileRepository.existsByLicenseNumber(request.getLicenseNumber())) {
            throw new RuntimeException("Số giấy phép lái xe đã được đăng ký!");
        }

        // check vehicle plate number exists
        if (driverProfileRepository.existsByVehiclePlateNumber(request.getVehiclePlateNumber())) {
            throw new RuntimeException("Biển số xe đã được đăng ký!");
        }

        // generate User entity
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .role(RoleType.DRIVER)
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        // generate DriverProfile entity
        DriverProfile driverProfile = DriverProfile.builder()
                .user(savedUser)
                .licenseNumber(request.getLicenseNumber())
                .licenseType(request.getLicenseType())
                .licenseExpiry(request.getLicenseExpiry())
                .vehicleType(request.getVehicleType())
                .vehicleModel(request.getVehicleModel())
                .vehiclePlateNumber(request.getVehiclePlateNumber())
                .vehicleColor(request.getVehicleColor())
                .vehicleYear(request.getVehicleYear())
                .vehicleCapacity(request.getVehicleCapacity())
                .identityCardNumber(request.getIdentityCardNumber())
                .identityCardFrontUrl(request.getIdentityCardFrontUrl())
                .identityCardBackUrl(request.getIdentityCardBackUrl())
                .licenseFrontUrl(request.getLicenseFrontUrl())
                .licenseBackUrl(request.getLicenseBackUrl())
                .vehicleRegistrationUrl(request.getVehicleRegistrationUrl())
                .insuranceUrl(request.getInsuranceUrl())
                .status(DriverStatus.OFFLINE)
                .isVerified(false)
                .isActive(true)
                .rating(5.0)
                .totalTrips(0)
                .completedTrips(0)
                .cancelledTrips(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        driverProfileRepository.save(driverProfile);

        return new MessageResponse("Đăng ký tài xế thành công! Vui lòng chờ xét duyệt.");
    }

    @Override
    public DriverProfileResponse verifyDriver(Long driverId, DriverVerificationRequest request, Long adminId) {
        DriverProfile driverProfile = driverProfileRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế!"));
        // check if already verified
        if (driverProfile.isVerified()) {
            throw new RuntimeException("Tài xế đã được xác thực trước đó!");
        }

        if ("APPROVE".equalsIgnoreCase(request.getAction())) {
            driverProfile.setVerified(true);
            driverProfile.setVerificationNote(request.getNote() != null ? request.getNote() : "Đã được duyệt");
            driverProfile.setVerifiedAt(LocalDateTime.now());
            driverProfile.setVerifiedBy(adminId);
        } else if ("REJECT".equalsIgnoreCase(request.getAction())) {
            driverProfile.setVerified(false);
            driverProfile.setActive(false);
            driverProfile.setVerificationNote(request.getNote() != null ? request.getNote() : "Bị từ chối");
        } else {
            throw new RuntimeException("Hành động không hợp lệ! Chỉ chấp nhận APPROVE hoặc REJECT");
        }

        driverProfile.setUpdatedAt(LocalDateTime.now());
        DriverProfile saved = driverProfileRepository.save(driverProfile);

        return convertToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverProfileResponse> getAvailableDrivers() {
        List<DriverProfile> drivers = driverProfileRepository
                .findByStatusAndIsVerifiedAndIsActive(DriverStatus.ONLINE, true, true);
        return drivers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverProfileResponse> getNearbyDrivers(Double latitude, Double longitude, int limit) {
        List<DriverProfile> drivers = driverProfileRepository
                .findNearbyDrivers(latitude, longitude, limit);
        return drivers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DriverProfileResponse> getPendingDrivers() {
        List<DriverProfile> drivers = driverProfileRepository
                .findByIsVerifiedAndIsActive(false, true);
        return drivers.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DriverProfileResponse getDriverProfile(Long driverId) {
        DriverProfile driverProfile = driverProfileRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế!"));
        return convertToResponse(driverProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public DriverProfileResponse getDriverProfileByUserId(Long userId) {
        DriverProfile driverProfile = driverProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy profile tài xế!"));
        return convertToResponse(driverProfile);
    }

    @Override
    public DriverProfileResponse updateDriverStatus(Long userId, DriverStatusUpdateRequest request) {
        DriverProfile driverProfile = driverProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy profile tài xế!"));

        if (!driverProfile.isVerified()) {
            throw new RuntimeException("Tài xế chưa được xác thực!");
        }

        if (!driverProfile.isActive()) {
            throw new RuntimeException("Tài khoản tài xế đã bị vô hiệu hóa!");
        }

        // Cập nhật trạng thái
        driverProfile.setStatus(request.getStatus());

        // Cập nhật vị trí nếu có
        if (request.getLatitude() != null && request.getLongitude() != null) {
            driverProfile.setCurrentLatitude(request.getLatitude());
            driverProfile.setCurrentLongitude(request.getLongitude());
            driverProfile.setLastLocationUpdate(LocalDateTime.now());
        }

        driverProfile.setUpdatedAt(LocalDateTime.now());
        DriverProfile saved = driverProfileRepository.save(driverProfile);

        return convertToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean canAcceptTrip(Long driverId) {
        DriverProfile driverProfile = driverProfileRepository.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tài xế!"));

        return driverProfile.isVerified()
                && driverProfile.isActive()
                && driverProfile.getStatus() == DriverStatus.ONLINE;
    }

    private DriverProfileResponse convertToResponse(DriverProfile driverProfile) {
        User user = driverProfile.getUser();

        return DriverProfileResponse.builder()
                .id(driverProfile.getId())
                .userId(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .licenseNumber(driverProfile.getLicenseNumber())
                .licenseType(driverProfile.getLicenseType())
                .licenseExpiry(driverProfile.getLicenseExpiry())
                .vehicleType(driverProfile.getVehicleType())
                .vehicleModel(driverProfile.getVehicleModel())
                .vehiclePlateNumber(driverProfile.getVehiclePlateNumber())
                .vehicleColor(driverProfile.getVehicleColor())
                .vehicleYear(driverProfile.getVehicleYear())
                .vehicleCapacity(driverProfile.getVehicleCapacity())
                .status(driverProfile.getStatus())
                .isVerified(driverProfile.isVerified())
                .isActive(driverProfile.isActive())
                .verificationNote(driverProfile.getVerificationNote())
                .verifiedAt(driverProfile.getVerifiedAt())
                .rating(driverProfile.getRating())
                .totalTrips(driverProfile.getTotalTrips())
                .completedTrips(driverProfile.getCompletedTrips())
                .cancelledTrips(driverProfile.getCancelledTrips())
                .currentLatitude(driverProfile.getCurrentLatitude())
                .currentLongitude(driverProfile.getCurrentLongitude())
                .lastLocationUpdate(driverProfile.getLastLocationUpdate())
                .createdAt(driverProfile.getCreatedAt())
                .updatedAt(driverProfile.getUpdatedAt())
                .build();
    }
}

