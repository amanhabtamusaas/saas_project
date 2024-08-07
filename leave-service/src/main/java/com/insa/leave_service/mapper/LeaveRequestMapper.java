package com.insa.leave_service.mapper;

import com.insa.leave_service.dto.request.LeaveRequestRequest;

import com.insa.leave_service.dto.response.LeaveRequestResponse;

import com.insa.leave_service.entity.LeaveRequest;
import com.insa.leave_service.entity.LeaveType;
import com.insa.leave_service.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class LeaveRequestMapper {

    private final LeaveTypeRepository leaveTypeRepository;

    public LeaveRequest toEntity(LeaveRequestRequest requestDTO) {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(requestDTO.getEmployeeId());
        leaveRequest.setNumberOfDays(requestDTO.getNumberOfDays());
        leaveRequest.setLeaveStart(requestDTO.getLeaveStart());
        leaveRequest.setDay(requestDTO.getDay());
        leaveRequest.setReturnDate(requestDTO.getReturnDate());
        leaveRequest.setDescription(requestDTO.getDescription());

        LeaveType leaveType = leaveTypeRepository.findById(requestDTO.getLeaveTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid leaveTypeId"));
        leaveRequest.setLeaveType(leaveType);

        return leaveRequest;
    }

    public LeaveRequestResponse toResponseDTO(LeaveRequest entity) {
        LeaveRequestResponse responseDTO = new LeaveRequestResponse();
        responseDTO.setId(entity.getId());
        responseDTO.setEmployeeId(entity.getEmployeeId());
        responseDTO.setNumberOfDays(entity.getNumberOfDays());
        responseDTO.setLeaveStart(entity.getLeaveStart());
        responseDTO.setDay(entity.getDay());
        responseDTO.setReturnDate(entity.getReturnDate());
        responseDTO.setDescription(entity.getDescription());
        responseDTO.setLeaveTypeId(entity.getLeaveType().getId());
        responseDTO.setTenantId(entity.getTenantId());
        return responseDTO;
    }

    public void updateLeaveRequest(LeaveRequest leaveRequest, LeaveRequestRequest requestDTO) {
        if (requestDTO.getEmployeeId() != null) {
            leaveRequest.setEmployeeId(requestDTO.getEmployeeId());
        }
        if (requestDTO.getNumberOfDays() != 0) {
            leaveRequest.setNumberOfDays(requestDTO.getNumberOfDays());
        }
        if (requestDTO.getLeaveStart() != null) {
            leaveRequest.setLeaveStart(requestDTO.getLeaveStart());
        }
        if (requestDTO.getDay() != null) {
            leaveRequest.setDay(requestDTO.getDay());
        }
        if (requestDTO.getReturnDate() != null) {
            leaveRequest.setReturnDate(requestDTO.getReturnDate());
        }
        if (requestDTO.getDescription() != null) {
            leaveRequest.setDescription(requestDTO.getDescription());
        }
        if (requestDTO.getLeaveTypeId() != null) {
            LeaveType leaveType = leaveTypeRepository.findById(requestDTO.getLeaveTypeId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid leaveTypeId"));
            leaveRequest.setLeaveType(leaveType);
        }
    }
}
