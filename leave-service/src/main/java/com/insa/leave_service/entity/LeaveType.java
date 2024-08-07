package com.insa.leave_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class LeaveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private Long tenantId;

}
