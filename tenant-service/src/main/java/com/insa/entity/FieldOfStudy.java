package com.insa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class FieldOfStudy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "field_of_study cannot be blank")
    @Column(name = "field_of_study", nullable = false)
    private String fieldOfStudy;
    @NotBlank(message = "description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;
    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    @ManyToOne
    @JoinColumn(name = "job_reg_id")
    private JobRegistration jobRegistration;
}
