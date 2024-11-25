package com.windear.app.enums;

public enum Status {
    DECLINE(0),
    ACCEPT(1),
    PENDING(2),
    SUBSCRIBE(3);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}