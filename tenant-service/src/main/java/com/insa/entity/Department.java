package com.insa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false, unique = true)
    private Long id;
    @NotBlank(message = "Department name cannot be blank")
    @Column(name = "department_name", nullable = false)
    private String departmentName;
    @NotNull(message = "Established date cannot be null")
    @Column(name = "established_date", nullable = false)
    private LocalDate establishedDate;
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
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
    @ManyToOne
    @JoinColumn(name = "department_type_id")
    private DepartmentType departmentType;
    @ManyToOne
    @JoinColumn(name = "parent_department_id")
    private Department parentDepartment;
    @OneToMany(mappedBy = "parentDepartment")
    private Set<Department> subDepartments = new HashSet<>();
    @OneToMany(mappedBy = "department")
    private List<DepartmentHistory> departmentHistory;
    @OneToMany(mappedBy = "department")
    private List<JobRegistration> jobRegistrations;
    @OneToMany(mappedBy = "department")
    private List<StaffPlan> staffPlans;
    @OneToMany(mappedBy = "department")
    private List<Address> addresses;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    // Ensures bidirectional relationship consistency
    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
        if (parentDepartment != null) {
            parentDepartment.getSubDepartments().add(this);
        }
    }

    // Adds a sub-department and sets the parent department
    public void addSubDepartment(Department subDepartment) {
        subDepartments.add(subDepartment);
        subDepartment.setParentDepartment(this);
    }

    // Removes a sub-department and clears its parent department reference
    public void removeSubDepartment(Department subDepartment) {
        subDepartments.remove(subDepartment);
        subDepartment.setParentDepartment(null);
    }

    // Retrieves all parent departments recursively
    public List<Department> getAllParentDepartments() {
        List<Department> parentDepartments = new ArrayList<>();
        Department currentDepartment = this;
        while (currentDepartment.getParentDepartment() != null) {
            currentDepartment = currentDepartment.getParentDepartment();
            parentDepartments.add(currentDepartment);
        }
        return parentDepartments;
    }

    // Retrieves all child departments recursively
    public List<Department> getAllChildDepartments() {
        List<Department> childDepartments = new ArrayList<>(subDepartments);
        for (Department subDepartment : subDepartments) {
            childDepartments.addAll(subDepartment.getAllChildDepartments());
        }
        return childDepartments;
    }
    public void changeParentDepartment(Department childDepartment, Department newParentDepartment) {
        // Remove the child from the current parent department if it has one
        if (childDepartment.getParentDepartment() != null) {
            Department oldParent = childDepartment.getParentDepartment();
            oldParent.getSubDepartments().remove(childDepartment);
        }

        // Set the new parent for the child department
        childDepartment.setParentDepartment(newParentDepartment);

        // Add the child to the new parent department's subDepartments set
        if (newParentDepartment != null) {
            newParentDepartment.getSubDepartments().add(childDepartment);
        }
    }

}