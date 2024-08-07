package com.insa.enums;

import lombok.Getter;

@Getter
public enum RecruitmentMode {

    INTERNAL("Internal"),
    EXTERNAL("External"),
    TRANSFER("Transfer"),
    OTHER("Other");

    private final String recruitmentMode;

    RecruitmentMode(String recruitmentMode) {
        this.recruitmentMode = recruitmentMode;
    }
}
