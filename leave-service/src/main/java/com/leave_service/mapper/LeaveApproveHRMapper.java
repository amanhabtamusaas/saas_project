package com.leave_service.mapper;

import com.leave_service.dto.request.LeaveApproveHRRequest;
import com.leave_service.dto.response.LeaveApproveHRResponse;
import com.leave_service.model.LeaveApproveDepartment;
import com.leave_service.model.LeaveApproveHR;
import com.leave_service.service.BudgetYearService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveApproveHRMapper {

    private final BudgetYearService budgetYearService;

    /**
     * Maps a LeaveApproveHRRequest to a LeaveApproveHR entity.
     *
     * @param request the LeaveApproveHRRequest to map
     * @return the mapped LeaveApproveHR entity
     */
    public LeaveApproveHR mapToEntity(LeaveApproveHRRequest request) {
        LeaveApproveHR leaveApproveHR = new LeaveApproveHR();
        leaveApproveHR.setDecision(request.getDecision());
        leaveApproveHR.setComment(request.getComment());
//
//        if (request.getBudgetYearId() != null) {
//            // Fetch BudgetYear entity by id
//            leaveApproveHR.setBudgetYearId(request.getBudgetYearId());
//        }
        if (request.getLeaveApproveDepartmentId() != null) {
            LeaveApproveDepartment approve = new LeaveApproveDepartment();
            approve.setId(request.getLeaveApproveDepartmentId());
            leaveApproveHR.setLeaveApproveDepartment(approve);
        }

        // Set other properties as needed, e.g., tenantId

        return leaveApproveHR;
    }

    /**
     * Maps a LeaveApproveHR entity to a LeaveApproveHRResponse.
     *
     * @param entity the LeaveApproveHR entity to map
     * @return the mapped LeaveApproveHRResponse
     */
    public LeaveApproveHRResponse mapToDto(LeaveApproveHR entity) {
        LeaveApproveHRResponse response = new LeaveApproveHRResponse();
        response.setId(entity.getId());
        response.setDecision(entity.getDecision());
        response.setComment(entity.getComment());

        if (entity.getLeaveApproveDepartment() != null) {
            response.setLeaveApproveDepartmentId(entity.getLeaveApproveDepartment().getId());
        }

        // Set other properties as needed, e.g., tenantId
        response.setTenantId(entity.getTenantId());

        return response;
    }

    /**
     * Updates an existing LeaveApproveHR entity with data from a LeaveApproveHRRequest.
     *
     * @param leaveApproveHR the existing LeaveApproveHR entity to update
     * @param request        the LeaveApproveHRRequest containing updated data
     */
    public void updateLeaveApproveHR(LeaveApproveHR leaveApproveHR, LeaveApproveHRRequest request) {
        leaveApproveHR.setDecision(request.getDecision());
        leaveApproveHR.setComment(request.getComment());

    }
}
