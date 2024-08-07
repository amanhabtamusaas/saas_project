package com.insa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PayGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_grade_id", nullable = false, unique = true)
    private Long id;

    @NotNull(message = "Initial salary cannot be null")
    @Column(name = "initial_salary", nullable = false)
    private Double initialSalary;
    @NotNull(message = "Maximum salary cannot be null")
    @Column(name = "maximum_salary", nullable = false)
    private Double maximumSalary;
    @NotNull(message = "Salary step cannot be null")
    @Column(name = "salary_step", nullable = false)
    private Integer salaryStep;
    private Double salary;
    @Column(name = "description")
    private String description;
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @CreatedBy
    @Column(name = "created_by", nullable = true, updatable = false)
    private String createdBy;
    @LastModifiedBy
    @Column(name = "updated_by", nullable = true)
    private String updatedBy;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    @ManyToOne
    @JoinColumn(name = "jobGrade_id")
    private JobGrade jobGrade;
}

