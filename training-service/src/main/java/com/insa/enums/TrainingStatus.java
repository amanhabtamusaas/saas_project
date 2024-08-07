package com.insa.enums;

import lombok.Getter;

@Getter
public enum TrainingStatus {

    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String trainingStatus;

    TrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }
}
