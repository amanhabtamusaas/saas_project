package com.insa.hr_planning_service.mapper;

import com.insa.hr_planning_service.dto.request.HrAnalysisRequestDto;
import com.insa.hr_planning_service.dto.response.HrAnalysisResponseDto;
import com.insa.hr_planning_service.entity.HrAnalysis;
import com.insa.hr_planning_service.entity.HrNeedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HrAnalysisMapper {

    /**
     * Maps a HrAnalysisRequestDTO to a HrAnalysis entity.
     *
     * @param request the HrAnalysisRequestDTO to map
     * @return the mapped HrAnalysis entity
     */
    public HrAnalysis mapToEntity(HrAnalysisRequestDto request) {
        HrAnalysis hrAnalysis = new HrAnalysis();
        hrAnalysis.setBudgetYearId(request.getBudgetYearId());
        hrAnalysis.setWorkUnitId(request.getWorkUnitId());
        hrAnalysis.setJobRegistrationId(request.getJobRegistrationId());

        if (request.getHrNeedRequestId() != null) {
            HrNeedRequest hrNeedRequest = new HrNeedRequest();
            hrNeedRequest.setId(request.getHrNeedRequestId());
            hrAnalysis.setHrNeedRequest(hrNeedRequest);
        }
        hrAnalysis.setProcessedBy(request.getProcessedBy());
        hrAnalysis.setComment(request.getComment());
        return hrAnalysis;
    }

    /**
     * Maps a HrAnalysis entity to a HrAnalysisResponseDTO.
     *
     * @param entity the HrAnalysis entity to map
     * @return the mapped HrAnalysisResponseDTO
     */
    public HrAnalysisResponseDto mapToDto(HrAnalysis entity) {
        HrAnalysisResponseDto response = new HrAnalysisResponseDto();
        response.setId(entity.getId());
        response.setBudgetYearId(entity.getBudgetYearId());
        response.setWorkUnitId(entity.getWorkUnitId());
        response.setJobRegistrationId(entity.getJobRegistrationId());
        response.setTenantId(entity.getTenantId());

        if (entity.getHrNeedRequest() != null) {
            response.setHrNeedRequestId(entity.getHrNeedRequest().getId());
        }

        response.setAnalysedOn(entity.getAnalysedOn());
        response.setProcessedDate(entity.getProcessedDate());
        response.setProcessedBy(entity.getProcessedBy());
        response.setComment(entity.getComment());
        return response;
    }

    /**
     * Updates an existing HrAnalysis entity with data from a HrAnalysisRequestDTO.
     *
     * @param hrAnalysis the existing HrAnalysis entity to update
     * @param request the HrAnalysisRequestDTO containing updated data
     */
    public void updateHrAnalysis(HrAnalysis hrAnalysis, HrAnalysisRequestDto request) {
        if (request.getBudgetYearId() != null) {
            hrAnalysis.setBudgetYearId(request.getBudgetYearId());
        }
        if (request.getWorkUnitId() != null) {
            hrAnalysis.setWorkUnitId(request.getWorkUnitId());
        }
        if (request.getJobRegistrationId() != null) {
            hrAnalysis.setJobRegistrationId(request.getJobRegistrationId());
        }

        if (request.getHrNeedRequestId() != null) {
            HrNeedRequest hrNeedRequest = new HrNeedRequest();
            hrNeedRequest.setId(request.getHrNeedRequestId());
            hrAnalysis.setHrNeedRequest(hrNeedRequest);
        }
        if (request.getProcessedBy() != null) {
            hrAnalysis.setProcessedBy(request.getProcessedBy());
        }
        if (request.getComment() != null) {
            hrAnalysis.setComment(request.getComment());
        }
    }
}
