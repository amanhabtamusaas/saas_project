package com.hr_planning_service.model;

import com.hr_planning_service.enums.EmploymentType;
import com.hr_planning_service.enums.HowToBeFilled;
import com.hr_planning_service.enums.WhenToBe;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class HrNeedRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "no_of_position", nullable = false)
    private Integer noOfPosition;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", nullable = false)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "how_to_be_filled", nullable = false)
    private HowToBeFilled howToBeFilled;

    @Enumerated(EnumType.STRING)
    @Column(name = "when_to_be", nullable = false)
    private WhenToBe whenToBe;

    @Column(name = "remark", length = 500)
    private String remark;

    @Column(name = "budget_year_id", nullable = false)
    private UUID budgetYearId;

    @Column(name = "department_id", nullable = false)
    private UUID departmentId;

    @Column(name = "staff_plan_id", nullable = false)
    private UUID staffPlanId;

    @Column(name = "tenant_id", nullable = false)
    private UUID tenantId;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by", nullable = true, updatable = false, length = 100)
    private String createdBy;

    @OneToMany(mappedBy = "hrNeedRequest", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<HrAnalysis> hrAnalyses = new ArrayList<>();

    @OneToMany(mappedBy = "hrNeedRequest", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<AnnualRecruitmentAndPromotion> annualRecruitmentAndPromotions = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
