package com.hr_planning_service.mapper;

import com.hr_planning_service.dto.request.HrAnalysisRequestDto;
import com.hr_planning_service.dto.response.HrAnalysisResponseDto;
import com.hr_planning_service.model.HrAnalysis;
import com.hr_planning_service.model.HrNeedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HrAnalysisMapper {

    /**
     * Maps a HrAnalysisRequestDTO to a HrAnalysis model.
     *
     * @param request the HrAnalysisRequestDTO to map
     * @return the mapped HrAnalysis model
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
     * Maps a HrAnalysis model to a HrAnalysisResponseDTO.
     *
     * @param model the HrAnalysis model to map
     * @return the mapped HrAnalysisResponseDTO
     */
    public HrAnalysisResponseDto mapToDto(HrAnalysis model) {
        HrAnalysisResponseDto response = new HrAnalysisResponseDto();
        response.setId(model.getId());
        response.setBudgetYearId(model.getBudgetYearId());
        response.setWorkUnitId(model.getWorkUnitId());
        response.setJobRegistrationId(model.getJobRegistrationId());
        response.setTenantId(model.getTenantId());

        if (model.getHrNeedRequest() != null) {
            response.setHrNeedRequestId(model.getHrNeedRequest().getId());
        }

        response.setAnalysedOn(model.getAnalysedOn());
        response.setProcessedDate(model.getProcessedDate());
        response.setProcessedBy(model.getProcessedBy());
        response.setComment(model.getComment());
        return response;
    }

    /**
     * Updates an existing HrAnalysis model with data from a HrAnalysisRequestDTO.
     *
     * @param hrAnalysis the existing HrAnalysis model to update
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
