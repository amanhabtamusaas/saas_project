package com.insa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class JobGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "job_grade_name  cannot be blank")
    @Column(name = "job_grade_name", nullable = false)
    private String jobGradeName;
    @NotBlank(message = "description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    @OneToMany(mappedBy = "jobGrade", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<JobRegistration> jobRegistrations;
    @OneToMany(mappedBy = "jobGrade", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PayGrade> payGrades;
//    @OneToMany(mappedBy = "jobGrade", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private List<StaffPlan> staffPlans;



}
