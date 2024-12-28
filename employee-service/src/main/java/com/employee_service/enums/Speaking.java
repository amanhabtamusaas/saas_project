package com.employee_service.enums;

import lombok.Getter;

@Getter
public enum Speaking {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    FLUENT("Fluent");

    private final String speaking;

    Speaking(String speaking) {
        this.speaking = speaking;
    }
}
