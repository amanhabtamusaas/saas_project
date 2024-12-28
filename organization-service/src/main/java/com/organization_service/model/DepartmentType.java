package com.organization_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class DepartmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank(message = "Department type name cannot be blank")
    @Column(name = "department_type_name", nullable = false)
    private String departmentTypeName;

    @NotBlank(message = "description cannot be blank")
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "departmentType", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Department> departments;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

}