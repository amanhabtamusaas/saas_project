package com.leave_service.model;

import com.leave_service.enums.Decision;
import jakarta.persistence.*;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class LeaveApproveDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

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
    private UUID tenantId;
}
