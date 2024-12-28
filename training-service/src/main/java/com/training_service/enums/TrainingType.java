package com.training_service.enums;

import lombok.Getter;

@Getter
public enum TrainingType {

    ANNUAL_TRAINING("Annual Training"),
    UNPLANNED_TRAINING("Unplanned Training");

    private final String trainingType;

    TrainingType(String trainingType) {
        this.trainingType = trainingType;
    }
}
