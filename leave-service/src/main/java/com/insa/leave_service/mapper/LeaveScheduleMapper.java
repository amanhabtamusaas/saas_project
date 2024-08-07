package com.insa.leave_service.mapper;

import com.insa.leave_service.dto.EmployeeDto;
import com.insa.leave_service.dto.request.LeaveScheduleRequest;
import com.insa.leave_service.dto.response.LeaveScheduleResponse;
import com.insa.leave_service.entity.LeaveSchedule;
import com.insa.leave_service.service.EmployeeServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveScheduleMapper {

    private final EmployeeServiceClient employeeServiceClient;

    public LeaveSchedule toLeaveSchedule(LeaveScheduleRequest request) {
        if (request == null) {
            return null;
        }

        LeaveSchedule leaveSchedule = new LeaveSchedule();
        // Map request fields to entity fields
        leaveSchedule.setEmployeeId(request.getEmployeeId());
        leaveSchedule.setLeaveMonth(request.getLeaveMonth());
        leaveSchedule.setDescription(request.getDescription());
       // leaveSchedule.setTenantId(request.getTenantId());

        // Fetch additional employee details if necessary


        return leaveSchedule;
    }

    /**
     * Maps a LeaveSchedule entity to a LeaveScheduleResponse.
     *
     * @param entity the LeaveSchedule entity to map
     * @return the mapped LeaveScheduleResponse
     */
    public LeaveScheduleResponse toLeaveScheduleResponse(LeaveSchedule entity) {
        if (entity == null) {
            return null;
        }

        LeaveScheduleResponse response = new LeaveScheduleResponse();
        // Map entity fields to response fields
        response.setId(entity.getId());
        response.setEmployeeId(entity.getEmployeeId());
        response.setLeaveMonth(entity.getLeaveMonth());
        response.setDescription(entity.getDescription());
        response.setTenantId(entity.getTenantId());

        return response;
    }

    /**
     * Updates an existing LeaveSchedule entity with data from a LeaveScheduleRequest.
     *
     * @param leaveSchedule the existing LeaveSchedule entity to update
     * @param request       the LeaveScheduleRequest containing updated data
     */
    public void updateLeaveSchedule(LeaveSchedule leaveSchedule, LeaveScheduleRequest request) {
        if (leaveSchedule == null || request == null) {
            return;
        }

        // Update entity fields with data from request
        leaveSchedule.setEmployeeId(request.getEmployeeId());
        leaveSchedule.setLeaveMonth(request.getLeaveMonth());
        leaveSchedule.setDescription(request.getDescription());
        //leaveSchedule.setTenantId(request.getTenantId());
    }
}