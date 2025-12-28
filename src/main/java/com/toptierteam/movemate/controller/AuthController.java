package com.toptierteam.movemate.controller;

import com.toptierteam.movemate.dto.request.LoginRequest;
import com.toptierteam.movemate.dto.request.RefreshTokenRequest;
import com.toptierteam.movemate.dto.request.RegisterRequest;
import com.toptierteam.movemate.dto.response.JwtResponse;
import com.toptierteam.movemate.dto.response.MessageResponse;
import com.toptierteam.movemate.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "API xác thực và quản lý người dùng")
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Đăng nhập",
            description = "Đăng nhập bằng username/password để nhận JWT token và refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng nhập thành công",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Thông tin đăng nhập không đúng",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @Operation(
            summary = "Đăng ký tài khoản mới",
            description = "Tạo tài khoản người dùng mới trong hệ thống"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng ký thành công",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dữ liệu đầu vào không hợp lệ hoặc username/email đã tồn tại",
                    content = @Content)
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @Operation(
            summary = "Làm mới access token",
            description = "Sử dụng refresh token để lấy access token mới khi token cũ hết hạn"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Làm mới token thành công",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "403", description = "Refresh token không hợp lệ hoặc đã hết hạn",
                    content = @Content)
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @Operation(
            summary = "Đăng xuất",
            description = "Đăng xuất và vô hiệu hóa refresh token hiện tại",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Đăng xuất thành công",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "401", description = "Chưa xác thực",
                    content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.logout(request));
    }
}
