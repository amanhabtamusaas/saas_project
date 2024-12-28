package com.recruitment_service.enums;

import lombok.Getter;

@Getter
public enum RecruitmentStatus {

    PENDING("Pending"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String recruitmentStatus;

    RecruitmentStatus(String recruitmentStatus) {
        this.recruitmentStatus = recruitmentStatus;
    }
}
