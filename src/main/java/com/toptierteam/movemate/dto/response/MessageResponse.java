package com.toptierteam.movemate.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response chứa thông báo")
public class MessageResponse {

    @Schema(description = "Thông báo", example = "Đăng ký thành công")
    private String message;
}
