package com.leave_service.dto.response;

import com.leave_service.enums.Decision;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApproveHRResponse {

    private UUID id;
    private Decision decision;
    private String comment;
    private UUID tenantId;
//    private UUID budgetYearId;
  //  private int approvedDays;
    private UUID leaveApproveDepartmentId;

}
