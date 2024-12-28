package com.recruitment_service.enums;

import lombok.Getter;

@Getter
public enum ApplicationStatus {

    OPEN("Open"),
    CLOSED("Closed"),
    RE_OPENED("Re-Opened"),
    NOT_OPENED("Not Opened");

    private final String applicationStatus;

    ApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
