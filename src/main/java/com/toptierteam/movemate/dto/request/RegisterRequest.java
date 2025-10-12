package com.toptierteam.movemate.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Schema(description = "Request đăng ký tài khoản")
public class RegisterRequest {

    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 20, message = "Username phải từ 3-20 ký tự")
    @Schema(description = "Tên đăng nhập", example = "user123")
    private String username;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Schema(description = "Email", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 6, max = 40, message = "Password phải từ 6-40 ký tự")
    @Schema(description = "Mật khẩu", example = "password123")
    private String password;

    @NotBlank(message = "Họ tên không được để trống")
    @Schema(description = "Họ và tên", example = "Nguyễn Văn A")
    private String fullName;

    @Schema(description = "Số điện thoại", example = "0987654321")
    private String phoneNumber;
}
