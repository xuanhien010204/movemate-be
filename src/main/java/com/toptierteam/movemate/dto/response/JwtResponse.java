package com.toptierteam.movemate.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response chứa JWT token")
public class JwtResponse {

    @Schema(description = "Access token", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String accessToken;

    @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzUxMiJ9...")
    private String refreshToken;

    @Builder.Default
    @Schema(description = "Loại token", example = "Bearer")
    private String tokenType = "Bearer";

    @Schema(description = "ID người dùng", example = "1")
    private Long id;

    @Schema(description = "Tên đăng nhập", example = "user123")
    private String username;

    @Schema(description = "Email", example = "user@example.com")
    private String email;

    @Schema(description = "Họ tên", example = "Nguyễn Văn A")
    private String fullName;
}
