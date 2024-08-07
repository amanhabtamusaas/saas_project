package com.insa.entity;

import com.insa.enums.JobType;
import com.insa.enums.ReportsTo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class JobRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_reg_id", nullable = false, unique = true)
    private Long id;
    private String jobTitle;
    private String jobCode;
    @Enumerated(EnumType.STRING)
    private ReportsTo reportsTo;
    @Enumerated(EnumType.STRING)
    private JobType jobType;
    @Min(value = 0, message = "Minimum experience must be greater than or equal to 0")
    @Column(name = "minimum_experience", nullable = false)
    private Integer minExperience;
    private String duties;
    private String language;
    private String skills;
    private String description;
    private String alternativeExperience;
    private String relativeExperience;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
    @ManyToOne
    @JoinColumn(name = "edu_level_id")
    private EducationLevel educationLevel;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "job_cat_id")
    private JobCategory jobCategory;
    @ManyToOne
    @JoinColumn(name = "job_grade_id")
    private JobGrade jobGrade;
    @ManyToOne
    @JoinColumn(name = "work_unit_id")
    private WorkUnit workUnit;
    @ManyToOne
    @JoinColumn(name = "qualification_id")
    private Qualification qualification;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    @OneToMany(mappedBy = "jobRegistration")
    private List<StaffPlan> staffPlans;


}