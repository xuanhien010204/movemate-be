package com.toptierteam.movemate.enums;

public enum MethodPayment {
    CASH("Tiền mặt"),
    CREDIT_CARD("Thẻ tín dụng"),
    DEBIT_CARD("Thẻ ghi nợ"),
    E_WALLET("Ví điện tử"),
    BANK_TRANSFER("Chuyển khoản ngân hàng"),
    MOMO("MoMo"),
    ZALOPAY("ZaloPay"),
    VNPAY("VNPay"),
    WALLET("Ví MoveMate");

    private final String description;

    MethodPayment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
