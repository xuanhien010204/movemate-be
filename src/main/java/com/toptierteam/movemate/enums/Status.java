package com.toptierteam.movemate.enums;

public enum Status {
    PENDING("Đang chờ"),
    CONFIRMED("Đã xác nhận"),
    IN_PROGRESS("Đang thực hiện"),
    COMPLETED("Hoàn thành"),
    CANCELLED("Đã hủy"),
    FAILED("Thất bại"),
    EXPIRED("Đã hết hạn"),
    ACTIVE("Hoạt động"),
    INACTIVE("Không hoạt động"),
    APPROVED("Đã duyệt"),
    REJECTED("Đã từ chối"),
    PROCESSING("Đang xử lý"),
    ON_HOLD("Tạm dừng"),
    REFUNDED("Đã hoàn tiền");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
