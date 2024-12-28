package com.training_service.enums;

import lombok.Getter;

@Getter
public enum InternshipStatus {

    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    INCOMPLETE("Incomplete");

    private final String internshipStatus;

    InternshipStatus(String internshipStatus) {
        this.internshipStatus = internshipStatus;
    }
}
