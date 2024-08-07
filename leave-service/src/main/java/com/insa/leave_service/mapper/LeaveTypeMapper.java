package com.insa.leave_service.mapper;

import com.insa.leave_service.dto.request.LeaveTypeRequest;

import com.insa.leave_service.dto.response.LeaveTypeResponse;

import com.insa.leave_service.entity.LeaveType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveTypeMapper {

    /**
     * Maps a LeaveTypeRequestDTO to a LeaveType entity.
     *
     * @param request the LeaveTypeRequestDTO to map
     * @param tenantId the tenant ID to set
     * @return the mapped LeaveType entity
     */
    public LeaveType toEntity(LeaveTypeRequest request, Long tenantId) {
        LeaveType leaveType = new LeaveType();
        leaveType.setLeaveTypeName(request.getLeaveTypeName());
        leaveType.setTenantId(tenantId);
        // Set other relationships if available
        // leaveType.setLeaveSetting(...);
        // leaveType.setLeaveRequest(...);
        // leaveType.setLeaveApproveHR(...);
        // leaveType.setLeaveApproveDepartment(...);
        return leaveType;
    }

    /**
     * Maps a LeaveType entity to a LeaveTypeResponseDTO.
     *
     * @param entity the LeaveType entity to map
     * @return the mapped LeaveTypeResponseDTO
     */
    public LeaveTypeResponse toResponseDTO(LeaveType entity) {
        LeaveTypeResponse response = new LeaveTypeResponse();
        response.setId(entity.getId());
        response.setLeaveTypeName(entity.getLeaveTypeName());
        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing LeaveType entity with data from a LeaveTypeRequestDTO.
     *
     * @param leaveType the existing LeaveType entity to update
     * @param request the LeaveTypeRequestDTO containing updated data
     */
    public void updateLeaveType(LeaveType leaveType, LeaveTypeRequest request) {
        if (request.getLeaveTypeName() != null) {
            leaveType.setLeaveTypeName(request.getLeaveTypeName());
        }
        // Update other fields if necessary
        // if (request.getLeaveSettingId() != null) {
        //     leaveType.setLeaveSetting(...);
        // }
        // if (request.getLeaveRequestId() != null) {
        //     leaveType.setLeaveRequest(...);
        // }
        // if (request.getLeaveApproveHRId() != null) {
        //     leaveType.setLeaveApproveHR(...);
        // }
        // if (request.getLeaveApproveDepartmentId() != null) {
        //     leaveType.setLeaveApproveDepartment(...);
        // }
    }
}
