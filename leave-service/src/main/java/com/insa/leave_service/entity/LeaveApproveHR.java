package com.insa.leave_service.entity;

import com.insa.leave_service.enums.Decision;
import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
@Data
@NoArgsConstructor
public class LeaveApproveHR {
    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Decision decision;

    @Size(max = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "leave_approve_department_id")
    private LeaveApproveDepartment leaveApproveDepartment;

    private Long tenantId;

//    private Long budgetYearId;

}
