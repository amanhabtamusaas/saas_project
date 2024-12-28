package com.employee_service.enums;

import lombok.Getter;

@Getter
public enum Writing {
    BEGINNER("Beginner"),
    INTERMEDIATE("Intermediate"),
    ADVANCED("Advanced"),
    PROFICIENT("Proficient");

    private final String writing;

    Writing(String writing) {
        this.writing = writing;
    }
}
