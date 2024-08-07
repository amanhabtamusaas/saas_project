package com.insa.leave_service.entity;

import com.insa.leave_service.entity.LeaveType;
import com.insa.leave_service.enums.Day;
import com.insa.leave_service.enums.Decision;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
public class LeaveApproveDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "decision")
    @Enumerated(EnumType.STRING)
    private Decision decision;
    @Column(name = "approve_days", nullable = false)
    private int approvedDays;
    @Size(max = 1000)
    @Column(name = "comment")
    private String comment;
    @OneToMany(mappedBy = "leaveApproveDepartment")
    private List<LeaveApproveHR> leaveApproveHRS;
    @ManyToOne
    @JoinColumn(name = "leaveRequest_id")
    private LeaveRequest leaveRequest;
    private Long tenantId;
}
