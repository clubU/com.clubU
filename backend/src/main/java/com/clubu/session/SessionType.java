package com.clubu.server.session;

public enum SessionType {
    STUDENT(1),
    CLUB(2);

    private final int value;

    SessionType(int value) {
        this.value = value;
    }

    public final int getTypeValue() {
        return value;
    }

}

