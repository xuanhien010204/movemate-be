package com.toptierteam.movemate.enums;

public enum DriverStatus {
    OFFLINE("Offline"),
    ONLINE("Online"),
    BUSY("Busy"),
    ON_TRIP("On Trip"),
    BREAK("Break");

    private final String description;

    DriverStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

