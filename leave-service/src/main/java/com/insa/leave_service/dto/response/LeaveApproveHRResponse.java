package com.insa.leave_service.dto.response;

import com.insa.leave_service.enums.Decision;
import lombok.*;

@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApproveHRResponse {

    private Long id;
    private Decision decision;
    private String comment;
    private Long tenantId;
//    private Long budgetYearId;
  //  private int approvedDays;
    private Long leaveApproveDepartmentId;

}
