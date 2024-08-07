package com.insa.enums;

import lombok.Getter;

@Getter
public enum ExperienceType {

    DIRECT("Direct Experience"),
    RELATED("Related Experience");

    private final String experienceType;

    ExperienceType(String experienceType) {
        this.experienceType = experienceType;
    }
}
