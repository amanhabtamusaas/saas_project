package com.training_service.enums;

import lombok.Getter;

@Getter
public enum TrainingLocation {

    LOCAL("Local"),
    ABROAD("Abroad");

    private final String trainingLocation;

    TrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }
}
