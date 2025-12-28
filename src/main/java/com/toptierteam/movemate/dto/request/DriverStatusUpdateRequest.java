package com.toptierteam.movemate.dto.request;

import com.toptierteam.movemate.enums.DriverStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverStatusUpdateRequest {

    @NotNull(message = "Trạng thái không được để trống")
    private DriverStatus status;

    private Double latitude;
    private Double longitude;
}

