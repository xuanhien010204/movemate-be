package com.toptierteam.movemate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "Request đăng nhập")
public class LoginRequest {

    @NotBlank(message = "Username không được để trống")
    @Schema(description = "Tên đăng nhập", example = "user123")
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Schema(description = "Mật khẩu", example = "password123")
    private String password;
}
