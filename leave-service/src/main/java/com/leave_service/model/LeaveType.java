package com.leave_service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String leaveTypeName;

    @OneToMany(mappedBy = "leaveType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LeaveSetting> leaveSettings;

    @ManyToOne
    @JoinColumn(name = "leaveApproveHR_id")
    private LeaveApproveHR leaveApproveHR;

    @ManyToOne
    @JoinColumn(name = "leaveApproveDepartment_id")
    private LeaveApproveDepartment leaveApproveDepartment;

    @OneToMany(mappedBy = "leaveType", cascade = CascadeType.ALL)
    private List<LeaveRequest> leaveRequests;

    private UUID tenantId;

}
