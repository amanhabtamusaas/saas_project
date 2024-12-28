package com.hr_planning_service.mapper;


import com.hr_planning_service.dto.request.NeedRequestRequestDto;
import com.hr_planning_service.dto.response.NeedRequestResponseDto;
import com.hr_planning_service.model.HrNeedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NeedRequestMapper {

    /**
     * Maps a NeedRequestRequestDTO to a NeedRequest model.
     *
     * @param request the NeedRequestRequestDTO to map
     * @return the mapped NeedRequest model
     */
    public HrNeedRequest mapToEntity(NeedRequestRequestDto request) {
        HrNeedRequest hrNeedRequest = new HrNeedRequest();
        hrNeedRequest.setNoOfPosition(request.getNoOfPosition());
        hrNeedRequest.setEmploymentType(request.getEmploymentType());
        hrNeedRequest.setHowToBeFilled(request.getHowToBeFilled());
        hrNeedRequest.setWhenToBe(request.getWhenToBe());
        hrNeedRequest.setRemark(request.getRemark());
        hrNeedRequest.setBudgetYearId(request.getBudgetYearId());
        hrNeedRequest.setDepartmentId(request.getDepartmentId());
        hrNeedRequest.setStaffPlanId(request.getStaffPlanId());
       // needRequest.setTenantId(request.getTenantId());
        return hrNeedRequest;
    }

    /**
     * Maps a NeedRequest model to a NeedRequestResponseDTO.
     *
     * @param model the NeedRequest model to map
     * @return the mapped NeedRequestResponseDTO
     */
    public NeedRequestResponseDto mapToDto(HrNeedRequest model) {
        NeedRequestResponseDto response = new NeedRequestResponseDto();
        response.setId(model.getId());
        response.setNoOfPosition(model.getNoOfPosition());
        response.setEmploymentType(model.getEmploymentType());
        response.setHowToBeFilled(model.getHowToBeFilled());
        response.setWhenToBe(model.getWhenToBe());
        response.setRemark(model.getRemark());
        response.setBudgetYearId(model.getBudgetYearId());
        response.setDepartmentId(model.getDepartmentId());
        response.setStaffPlanId(model.getStaffPlanId());
        response.setTenantId(model.getTenantId());
        response.setCreatedAt(model.getCreatedAt());
        response.setCreatedBy(model.getCreatedBy());
        return response;
    }

    /**
     * Updates an existing NeedRequest model with data from a NeedRequestRequestDTO.
     *
     * @param hrNeedRequest the existing NeedRequest model to update
     * @param request the NeedRequestRequestDTO containing updated data
     */
    public void updateNeedRequest(HrNeedRequest hrNeedRequest, NeedRequestRequestDto request) {
        if (request.getNoOfPosition() != null) {
            hrNeedRequest.setNoOfPosition(request.getNoOfPosition());
        }
        if (request.getEmploymentType() != null) {
            hrNeedRequest.setEmploymentType(request.getEmploymentType());
        }
        if (request.getHowToBeFilled() != null) {
            hrNeedRequest.setHowToBeFilled(request.getHowToBeFilled());
        }
        if (request.getWhenToBe() != null) {
            hrNeedRequest.setWhenToBe(request.getWhenToBe());
        }
        if (request.getRemark() != null) {
            hrNeedRequest.setRemark(request.getRemark());
        }
        if (request.getBudgetYearId() != null) {
            hrNeedRequest.setBudgetYearId(request.getBudgetYearId());
        }
        if (request.getDepartmentId() != null) {
            hrNeedRequest.setDepartmentId(request.getDepartmentId());
        }
        if (request.getStaffPlanId() != null) {
            hrNeedRequest.setStaffPlanId(request.getStaffPlanId());
        }


    }
}
