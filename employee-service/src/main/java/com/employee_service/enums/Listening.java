package com.employee_service.enums;

import lombok.Getter;

@Getter
public enum Listening {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    FLUENT("Fluent");

    private final String listening;

    Listening(String listening) {
        this.listening = listening;
    }
}

