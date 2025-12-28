package com.toptierteam.movemate.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverRegisterRequest {

    // User Information
    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 50, message = "Username phải từ 3-50 ký tự")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 100, message = "Mật khẩu phải từ 6-100 ký tự")
    private String password;

    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    private String address;

    // Driver License Information
    @NotBlank(message = "Số giấy phép lái xe không được để trống")
    private String licenseNumber;

    @NotBlank(message = "Loại giấy phép lái xe không được để trống")
    private String licenseType;

    @NotNull(message = "Ngày hết hạn giấy phép không được để trống")
    @Future(message = "Giấy phép lái xe phải còn hiệu lực")
    private LocalDateTime licenseExpiry;

    // Vehicle Information
    @NotBlank(message = "Loại xe không được để trống")
    private String vehicleType;

    @NotBlank(message = "Biển số xe không được để trống")
    private String vehiclePlateNumber;

    @NotNull(message = "Trọng tải xe không được để trống")
    @DecimalMin(value = "0.1", message = "Trọng tải phải lớn hơn 0")
    private Double vehicleCapacity;

    private String vehicleModel;
    private String vehicleColor;
    private Integer vehicleYear;

    // Identity Documents
    @NotBlank(message = "Số CMND/CCCD không được để trống")
    private String identityCardNumber;

    // Document URLs (uploaded to server first)
    private String identityCardFrontUrl;
    private String identityCardBackUrl;
    private String licenseFrontUrl;
    private String licenseBackUrl;
    private String vehicleRegistrationUrl;
    private String insuranceUrl;
}

