package com.insa.hr_planning_service.mapper;
import com.insa.hr_planning_service.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.insa.hr_planning_service.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.insa.hr_planning_service.entity.AnnualRecruitmentAndPromotion;
import com.insa.hr_planning_service.entity.HrNeedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class AnnualRecruitmentAndPromotionMapper {

    public AnnualRecruitmentAndPromotion toEntity(AnnualRecruitmentAndPromotionRequest requestDTO) {
        AnnualRecruitmentAndPromotion entity = new AnnualRecruitmentAndPromotion();
        entity.setBudgetYearId(requestDTO.getBudgetYearId());
        entity.setWorkUnitId(requestDTO.getWorkUnitId());
//        entity.setTenantId(requestDTO.getTenantId());
        entity.setGrandTotal(requestDTO.getGrandTotal());
        entity.setRemark(requestDTO.getRemark());
//        entity.setProcessedDate(requestDTO.getProcessedDate());
        entity.setPreparedBy(requestDTO.getPreparedBy());
        entity.setComment(requestDTO.getComment());

        if (requestDTO.getHrNeedRequestId() != null) {
            HrNeedRequest hrNeedRequest = new HrNeedRequest();
            hrNeedRequest.setId(requestDTO.getHrNeedRequestId());
            entity.setHrNeedRequest(hrNeedRequest);
        }

        entity.setInternalRecruitment(requestDTO.getInternalRecruitment());
        entity.setExternalRecruitment(requestDTO.getExternalRecruitment());
        entity.setAll(requestDTO.getAll());

        return entity;
    }

    public AnnualRecruitmentAndPromotionResponse toDto(AnnualRecruitmentAndPromotion entity) {
        AnnualRecruitmentAndPromotionResponse responseDTO = new AnnualRecruitmentAndPromotionResponse();
        responseDTO.setId(entity.getId());
        responseDTO.setBudgetYearId(entity.getBudgetYearId());
        responseDTO.setWorkUnitId(entity.getWorkUnitId());
        responseDTO.setTenantId(entity.getTenantId());
        responseDTO.setGrandTotal(entity.getGrandTotal());
        responseDTO.setRemark(entity.getRemark());
        responseDTO.setProcessedDate(entity.getProcessedDate());
        responseDTO.setPreparedBy(entity.getPreparedBy());
        responseDTO.setComment(entity.getComment());

        if (entity.getHrNeedRequest() != null) {
            responseDTO.setHrNeedRequestId(entity.getHrNeedRequest().getId());
        }

        responseDTO.setInternalRecruitment(entity.getInternalRecruitment());
        responseDTO.setExternalRecruitment(entity.getExternalRecruitment());
        responseDTO.setAll(entity.getAll());

        return responseDTO;
    }

    public void updateEntity(AnnualRecruitmentAndPromotion entity, AnnualRecruitmentAndPromotionRequest requestDTO) {
        if (requestDTO.getBudgetYearId() != null) {
            entity.setBudgetYearId(requestDTO.getBudgetYearId());
        }
        if (requestDTO.getWorkUnitId() != null) {
            entity.setWorkUnitId(requestDTO.getWorkUnitId());
        }

        if (requestDTO.getGrandTotal() != null) {
            entity.setGrandTotal(requestDTO.getGrandTotal());
        }
        if (requestDTO.getRemark() != null) {
            entity.setRemark(requestDTO.getRemark());
        }

        if (requestDTO.getPreparedBy() != null) {
            entity.setPreparedBy(requestDTO.getPreparedBy());
        }
        if (requestDTO.getComment() != null) {
            entity.setComment(requestDTO.getComment());
        }
        if (requestDTO.getHrNeedRequestId() != null) {
            HrNeedRequest hrNeedRequest = new HrNeedRequest();
            hrNeedRequest.setId(requestDTO.getHrNeedRequestId());
            entity.setHrNeedRequest(hrNeedRequest);
        }

        entity.setInternalRecruitment(requestDTO.getInternalRecruitment());
        entity.setExternalRecruitment(requestDTO.getExternalRecruitment());
        entity.setAll(requestDTO.getAll());
    }
}
