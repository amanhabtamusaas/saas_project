package com.hr_planning_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class HrAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID budgetYearId;

    @Column(nullable = false)
    private UUID workUnitId;

    @Column(nullable = false)
    private UUID jobRegistrationId;

    @Column(nullable = false)
    private UUID tenantId;

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
