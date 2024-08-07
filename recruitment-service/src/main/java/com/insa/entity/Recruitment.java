package com.insa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.insa.enums.RecruitmentMode;
import com.insa.enums.RecruitmentStatus;
import com.insa.enums.RecruitmentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(callSuper = true,
        exclude = { "advertisement", "assessmentWeight"})
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recruitment extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitment_id", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Requester employee id cannot be null")
    private Long requesterEmployeeId;

    @NotNull(message = "Department id cannot be null")
    private Long departmentId;

    @NotNull(message = "Job id cannot be null")
    private Long jobId;

    @Min (value = 1, message = "Number of requested employees must be minimum 1")
    private Integer numberOfEmployeesRequested;

    @NotNull(message = "Recruitment type cannot be null")
    @Enumerated(EnumType.STRING)
    private RecruitmentType recruitmentType;

    @NotNull(message = "Recruitment mode cannot be null")
    @Enumerated(EnumType.STRING)
    private RecruitmentMode recruitmentMode;

    private Integer numberOfEmployeesApproved;

    private String vacancyNumber;

    @NotNull(message = "Recruitment mode cannot be null")
    @Enumerated(EnumType.STRING)
    private RecruitmentStatus recruitmentStatus;

    private String remark;

    @OneToOne(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Advertisement advertisement;

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Applicant> applicants;

    @OneToOne(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private AssessmentWeight assessmentWeight;

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExamResult> examResult;

    @OneToMany(mappedBy = "recruitment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ShortlistCriteria> shortlistCriteria;
}
