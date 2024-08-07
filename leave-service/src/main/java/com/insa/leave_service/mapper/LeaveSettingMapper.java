package com.insa.leave_service.mapper;

import com.insa.leave_service.dto.request.LeaveSettingRequest;

import com.insa.leave_service.dto.response.LeaveSettingResponse;
//import com.insa.leave_service.dto.response.LeaveSettingResponseDTO;
import com.insa.leave_service.entity.LeaveSetting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveSettingMapper {

    /**
     * Maps a LeaveSettingRequestDTO to a LeaveSetting entity.
     *
     * @param request the LeaveSettingRequestDTO to map
     * @return the mapped LeaveSetting entity
     */
    public LeaveSetting toLeaveSetting(LeaveSettingRequest request) {
        LeaveSetting leaveSetting = new LeaveSetting();
        // Map request fields to entity fields
        leaveSetting.setGender(request.getGender());
        leaveSetting.setEmploymentType(request.getEmploymentType());
        leaveSetting.setMinimumDays(request.getMinimumDays());
        leaveSetting.setMaximumDays(request.getMaximumDays());
        leaveSetting.setRemark(request.getRemark());
        leaveSetting.setToBalance(request.getToBalance());
        leaveSetting.setEscapeSunday(request.getEscapeSunday());
        leaveSetting.setEscapeSaturday(request.getEscapeSaturday());
        leaveSetting.setEscapeHoliday(request.getEscapeHoliday());
       // leaveSetting.setTenantId(request.getTenantId());
        return leaveSetting;
    }


    /**
     * Maps a LeaveSetting entity to a LeaveSettingResponseDTO.
     *
     * @param entity the LeaveSetting entity to map
     * @return the mapped LeaveSettingResponseDTO
     */
    public LeaveSettingResponse toLeaveSettingResponseDTO(LeaveSetting entity) {
        LeaveSettingResponse response = new LeaveSettingResponse();
        // Map entity fields to response fields
        response.setId(entity.getId());
        response.setGender(entity.getGender());
        response.setEmploymentType(entity.getEmploymentType());
        response.setMinimumDays(entity.getMinimumDays());
        response.setMaximumDays(entity.getMaximumDays());
        response.setRemark(entity.getRemark());
        response.setToBalance(entity.getToBalance());
        response.setEscapeSunday(entity.getEscapeSunday());
        response.setEscapeSaturday(entity.getEscapeSaturday());
        response.setEscapeHoliday(entity.getEscapeHoliday());
        response.setTenantId(entity.getTenantId());
        return response;
    }

    /**
     * Updates an existing LeaveSetting entity with data from a LeaveSettingRequestDTO.
     *
     * @param leaveSetting the existing LeaveSetting entity to update
     * @param request      the LeaveSettingRequestDTO containing updated data
     */
    public void updateLeaveSetting(LeaveSetting leaveSetting, LeaveSettingRequest request) {
        // Update entity fields with data from request
        leaveSetting.setGender(request.getGender());
        leaveSetting.setEmploymentType(request.getEmploymentType());
        leaveSetting.setMinimumDays(request.getMinimumDays());
        leaveSetting.setMaximumDays(request.getMaximumDays());
        leaveSetting.setRemark(request.getRemark());
        leaveSetting.setToBalance(request.getToBalance());
        leaveSetting.setEscapeSunday(request.getEscapeSunday());
        leaveSetting.setEscapeSaturday(request.getEscapeSaturday());
        leaveSetting.setEscapeHoliday(request.getEscapeHoliday());
        //leaveSetting.setTenantId(request.getTenantId());
    }
}
