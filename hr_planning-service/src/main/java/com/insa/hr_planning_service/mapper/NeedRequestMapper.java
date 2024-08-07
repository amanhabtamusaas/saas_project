package com.insa.hr_planning_service.mapper;


import com.insa.hr_planning_service.dto.request.NeedRequestRequestDto;
import com.insa.hr_planning_service.dto.response.NeedRequestResponseDto;
import com.insa.hr_planning_service.entity.HrNeedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NeedRequestMapper {

    /**
     * Maps a NeedRequestRequestDTO to a NeedRequest entity.
     *
     * @param request the NeedRequestRequestDTO to map
     * @return the mapped NeedRequest entity
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
     * Maps a NeedRequest entity to a NeedRequestResponseDTO.
     *
     * @param entity the NeedRequest entity to map
     * @return the mapped NeedRequestResponseDTO
     */
    public NeedRequestResponseDto mapToDto(HrNeedRequest entity) {
        NeedRequestResponseDto response = new NeedRequestResponseDto();
        response.setId(entity.getId());
        response.setNoOfPosition(entity.getNoOfPosition());
        response.setEmploymentType(entity.getEmploymentType());
        response.setHowToBeFilled(entity.getHowToBeFilled());
        response.setWhenToBe(entity.getWhenToBe());
        response.setRemark(entity.getRemark());
        response.setBudgetYearId(entity.getBudgetYearId());
        response.setDepartmentId(entity.getDepartmentId());
        response.setStaffPlanId(entity.getStaffPlanId());
        response.setTenantId(entity.getTenantId());
        response.setCreatedAt(entity.getCreatedAt());
        response.setCreatedBy(entity.getCreatedBy());
        return response;
    }

    /**
     * Updates an existing NeedRequest entity with data from a NeedRequestRequestDTO.
     *
     * @param hrNeedRequest the existing NeedRequest entity to update
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
