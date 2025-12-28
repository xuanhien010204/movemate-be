package com.toptierteam.movemate.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverVerificationRequest {

    @NotBlank(message = "Trạng thái xác thực không được để trống")
    private String action; // "APPROVE" or "REJECT"

    private String note; // note for driver
}
