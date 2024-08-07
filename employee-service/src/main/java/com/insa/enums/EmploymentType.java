package com.insa.enums;

import lombok.Getter;

@Getter
public enum EmploymentType {
    PERMANENT("Permanent"),
    CONTRACT("Contract");

    private final String employmentType;

    EmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }
}
