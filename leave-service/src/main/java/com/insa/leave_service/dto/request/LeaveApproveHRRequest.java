package com.insa.leave_service.dto.request;

import com.insa.leave_service.dto.response.LeaveApproveDepartmentResponse;
import com.insa.leave_service.enums.Decision;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApproveHRRequest {
    private Decision decision;
    //private int approvedDays;
    @Size(max = 1000)
    private String comment;
    //private int approveDays;
//    private Long budgetYearId;
    private Long leaveApproveDepartmentId;

}
