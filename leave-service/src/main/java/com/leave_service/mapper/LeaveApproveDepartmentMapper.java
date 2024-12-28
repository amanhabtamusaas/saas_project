package com.leave_service.mapper;

import com.leave_service.dto.request.LeaveApproveDepartmentRequest;
import com.leave_service.dto.response.LeaveApproveDepartmentResponse;
import com.leave_service.model.LeaveApproveDepartment;
import com.leave_service.model.LeaveRequest;
import com.leave_service.enums.Decision;
import org.springframework.stereotype.Component;

@Component
public class LeaveApproveDepartmentMapper {

    /**
     * Maps a LeaveApproveDepartmentRequest to a LeaveApproveDepartment entity.
     *
     * @param request the LeaveApproveDepartmentRequest to map
     * @return the mapped LeaveApproveDepartment entity
     */
    public LeaveApproveDepartment toEntity(LeaveApproveDepartmentRequest request) {
        LeaveApproveDepartment leaveApproveDepartment = new LeaveApproveDepartment();
        leaveApproveDepartment.setDecision(Decision.valueOf(String.valueOf(request.getDecision())));
        leaveApproveDepartment.setApprovedDays(request.getApprovedDays());
        leaveApproveDepartment.setComment(request.getComment());
      //  leaveApproveDepartment.setTenantId(request.getTenantId()); // Set tenantId from request
        if (request.getLeaveRequestId() != null) {
            LeaveRequest approve = new LeaveRequest();
            approve.setId(request.getLeaveRequestId());
            leaveApproveDepartment.setLeaveRequest(approve);
        }
        return leaveApproveDepartment;
    }

    /**
     * Maps a LeaveApproveDepartment entity to a LeaveApproveDepartmentResponse.
     *
     * @param entity the LeaveApproveDepartment entity to map
     * @return the mapped LeaveApproveDepartmentResponse
     */
    public LeaveApproveDepartmentResponse toDto(LeaveApproveDepartment entity) {
        LeaveApproveDepartmentResponse response = new LeaveApproveDepartmentResponse();
        response.setId(entity.getId());
        response.setDecision(entity.getDecision().name());
        response.setApprovedDays(entity.getApprovedDays());
        response.setComment(entity.getComment());
        if (entity.getLeaveRequest() != null) {
            response.setLeaveRequestId(entity.getLeaveRequest().getId());
        }

        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing LeaveApproveDepartment entity with data from a LeaveApproveDepartmentRequest.
     *
     * @param leaveApproveDepartment the existing LeaveApproveDepartment entity to update
     * @param request                the LeaveApproveDepartmentRequest containing updated data
     */
    public void updateLeaveApproveDepartment(LeaveApproveDepartment leaveApproveDepartment, LeaveApproveDepartmentRequest request) {
        leaveApproveDepartment.setDecision(request.getDecision());
        leaveApproveDepartment.setApprovedDays(request.getApprovedDays());
        leaveApproveDepartment.setComment(request.getComment());
       // leaveApproveDepartment.setTenantId(request.getTenantId()); // Update tenantId from request
    }
}
