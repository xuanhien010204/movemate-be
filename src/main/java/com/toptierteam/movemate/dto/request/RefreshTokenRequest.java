package com.toptierteam.movemate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@Schema(description = "Request refresh token")
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token không được để trống")
    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String refreshToken;
}
