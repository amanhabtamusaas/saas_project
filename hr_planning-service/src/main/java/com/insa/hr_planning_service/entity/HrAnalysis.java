package com.insa.hr_planning_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HrAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long budgetYearId;

    @Column(nullable = false)
    private Long workUnitId;

    @Column(nullable = false)
    private Long jobRegistrationId;

    @Column(nullable = false)
    private Long tenantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_need_request_id", nullable = false)
    private HrNeedRequest hrNeedRequest;

    private LocalDate analysedOn;

    private LocalDate processedDate;

    @Column(length = 100)
    private String processedBy;

    @Column(length = 500)
    private String comment;
}
