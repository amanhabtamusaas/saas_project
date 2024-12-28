package com.recruitment_service.enums;

import lombok.Getter;

@Getter
public enum EducationType {
    REGULAR("Regular"),
    WEEKEND("Weekend"),
    NIGHT("Night"),
    DISTANCE("Distance"),
    SUMMER("Summer");

    private final String educationType;

    EducationType(String educationType) {
        this.educationType = educationType;
    }
}
