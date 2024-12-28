package com.recruitment_service.enums;

import lombok.Getter;

@Getter
public enum RecruitmentType {

    PERMANENT("Permanent"),
    CONTRACT("Contract"),
    TEMPORARY("Temporary"),
    INTERNSHIP("Internship"),
    OTHER("Other");

    private final String recruitmentType;

    RecruitmentType(String recruitmentType) {
        this.recruitmentType = recruitmentType;
    }
}
