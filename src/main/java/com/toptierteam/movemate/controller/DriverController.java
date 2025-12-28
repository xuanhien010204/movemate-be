package com.toptierteam.movemate.controller;

import com.toptierteam.movemate.dto.request.DriverRegisterRequest;
import com.toptierteam.movemate.dto.request.DriverStatusUpdateRequest;
import com.toptierteam.movemate.dto.request.DriverVerificationRequest;
import com.toptierteam.movemate.dto.response.DriverProfileResponse;
import com.toptierteam.movemate.dto.response.MessageResponse;
import com.toptierteam.movemate.security.UserPrincipal;
import com.toptierteam.movemate.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
@Tag(name = "Driver Management", description = "API quản lý tài xế")
public class DriverController {

    private final DriverService driverService;

    @PostMapping("/register")
    @Operation(
            summary = "Đăng ký làm tài xế",
            description = "Đăng ký tài khoản tài xế mới với thông tin xe và giấy tờ. Không cần xác thực, admin sẽ duyệt sau."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng ký thành công, chờ admin duyệt",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ hoặc username đã tồn tại",
                    content = @Content)
    })
    public ResponseEntity<MessageResponse> registerDriver(@Valid @RequestBody DriverRegisterRequest request) {
        return ResponseEntity.ok(driverService.registerDriver(request));
    }

    @PostMapping("/{driverId}/verify")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Xác thực tài xế",
            description = "Admin duyệt (approved=true) hoặc từ chối (approved=false) đơn đăng ký tài xế"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xác thực thành công",
                    content = @Content(schema = @Schema(implementation = DriverProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Không có quyền admin",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy tài xế",
                    content = @Content)
    })
    public ResponseEntity<DriverProfileResponse> verifyDriver(
            @PathVariable Long driverId,
            @Valid @RequestBody DriverVerificationRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(driverService.verifyDriver(driverId, request, currentUser.getId()));
    }

    @GetMapping("/available")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Lấy danh sách tài xế sẵn sàng",
            description = "Lấy tất cả tài xế có trạng thái ONLINE và đã được xác thực"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công",
                    content = @Content(schema = @Schema(implementation = DriverProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content)
    })
    public ResponseEntity<List<DriverProfileResponse>> getAvailableDrivers() {
        return ResponseEntity.ok(driverService.getAvailableDrivers());
    }

    @GetMapping("/nearby")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Tìm tài xế gần vị trí",
            description = "Tìm tài xế đang ONLINE gần nhất dựa trên tọa độ latitude/longitude"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm thành công",
                    content = @Content(schema = @Schema(implementation = DriverProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Tọa độ không hợp lệ",
                    content = @Content)
    })
    public ResponseEntity<List<DriverProfileResponse>> getNearbyDrivers(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(driverService.getNearbyDrivers(latitude, longitude, limit));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Lấy danh sách tài xế chờ duyệt",
            description = "Admin xem tất cả tài xế chưa được xác thực (isVerified = false)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công",
                    content = @Content(schema = @Schema(implementation = DriverProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Không có quyền admin",
                    content = @Content)
    })
    public ResponseEntity<List<DriverProfileResponse>> getPendingDrivers() {
        return ResponseEntity.ok(driverService.getPendingDrivers());
    }

    @GetMapping("/{driverId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DRIVER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Lấy thông tin tài xế",
            description = "Xem chi tiết profile tài xế theo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công",
                    content = @Content(schema = @Schema(implementation = DriverProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy tài xế",
                    content = @Content)
    })
    public ResponseEntity<DriverProfileResponse> getDriverProfile(@PathVariable Long driverId) {
        return ResponseEntity.ok(driverService.getDriverProfile(driverId));
    }

    @GetMapping("/profile/me")
    @PreAuthorize("hasRole('DRIVER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Lấy profile tài xế của chính mình",
            description = "Driver lấy thông tin profile của chính mình"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy thông tin thành công",
                    content = @Content(schema = @Schema(implementation = DriverProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy profile tài xế",
                    content = @Content)
    })
    public ResponseEntity<DriverProfileResponse> getMyProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(driverService.getDriverProfileByUserId(currentUser.getId()));
    }

    @PutMapping("/status")
    @PreAuthorize("hasRole('DRIVER')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Cập nhật trạng thái tài xế",
            description = "Driver thay đổi trạng thái hoạt động: ONLINE, OFFLINE, BREAK, hoặc BUSY"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công",
                    content = @Content(schema = @Schema(implementation = DriverProfileResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Trạng thái không hợp lệ",
                    content = @Content)
    })
    public ResponseEntity<DriverProfileResponse> updateStatus(
            @Valid @RequestBody DriverStatusUpdateRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        return ResponseEntity.ok(driverService.updateDriverStatus(currentUser.getId(), request));
    }

    @GetMapping("/{driverId}/can-accept-trip")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Boolean> canAcceptTrip(@PathVariable Long driverId) {
        return ResponseEntity.ok(driverService.canAcceptTrip(driverId));
    }
}

