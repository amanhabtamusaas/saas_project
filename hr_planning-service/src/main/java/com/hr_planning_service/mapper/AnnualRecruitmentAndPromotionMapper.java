package com.hr_planning_service.mapper;
import com.hr_planning_service.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.hr_planning_service.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.hr_planning_service.model.AnnualRecruitmentAndPromotion;
import com.hr_planning_service.model.HrNeedRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class AnnualRecruitmentAndPromotionMapper {

    public AnnualRecruitmentAndPromotion mapToEntity(AnnualRecruitmentAndPromotionRequest requestDTO) {
        AnnualRecruitmentAndPromotion model = new AnnualRecruitmentAndPromotion();
        model.setBudgetYearId(requestDTO.getBudgetYearId());
        model.setWorkUnitId(requestDTO.getWorkUnitId());
//        model.setTenantId(requestDTO.getTenantId());
        model.setGrandTotal(requestDTO.getGrandTotal());
        model.setRemark(requestDTO.getRemark());
//        model.setProcessedDate(requestDTO.getProcessedDate());
        model.setPreparedBy(requestDTO.getPreparedBy());
        model.setComment(requestDTO.getComment());

        if (requestDTO.getHrNeedRequestId() != null) {
            HrNeedRequest hrNeedRequest = new HrNeedRequest();
            hrNeedRequest.setId(requestDTO.getHrNeedRequestId());
            model.setHrNeedRequest(hrNeedRequest);
        }

        model.setInternalRecruitment(requestDTO.getInternalRecruitment());
        model.setExternalRecruitment(requestDTO.getExternalRecruitment());
        model.setIsAll(requestDTO.getAll());

        return model;
    }

    public AnnualRecruitmentAndPromotionResponse toDto(AnnualRecruitmentAndPromotion model) {
        AnnualRecruitmentAndPromotionResponse responseDTO = new AnnualRecruitmentAndPromotionResponse();
        responseDTO.setId(model.getId());
        responseDTO.setBudgetYearId(model.getBudgetYearId());
        responseDTO.setWorkUnitId(model.getWorkUnitId());
        responseDTO.setTenantId(model.getTenantId());
        responseDTO.setGrandTotal(model.getGrandTotal());
        responseDTO.setRemark(model.getRemark());
        responseDTO.setProcessedDate(model.getProcessedDate());
        responseDTO.setPreparedBy(model.getPreparedBy());
        responseDTO.setComment(model.getComment());

        if (model.getHrNeedRequest() != null) {
            responseDTO.setHrNeedRequestId(model.getHrNeedRequest().getId());
        }

        responseDTO.setInternalRecruitment(model.getInternalRecruitment());
        responseDTO.setExternalRecruitment(model.getExternalRecruitment());
        responseDTO.setAll(model.getIsAll());

        return responseDTO;
    }

    public void updateEntity(AnnualRecruitmentAndPromotion model,
                             AnnualRecruitmentAndPromotionRequest requestDTO) {

        if (requestDTO.getBudgetYearId() != null) {
            model.setBudgetYearId(requestDTO.getBudgetYearId());
        }
        if (requestDTO.getWorkUnitId() != null) {
            model.setWorkUnitId(requestDTO.getWorkUnitId());
        }

        if (requestDTO.getGrandTotal() != null) {
            model.setGrandTotal(requestDTO.getGrandTotal());
        }
        if (requestDTO.getRemark() != null) {
            model.setRemark(requestDTO.getRemark());
        }

        if (requestDTO.getPreparedBy() != null) {
            model.setPreparedBy(requestDTO.getPreparedBy());
        }
        if (requestDTO.getComment() != null) {
            model.setComment(requestDTO.getComment());
        }
        if (requestDTO.getHrNeedRequestId() != null) {
            HrNeedRequest hrNeedRequest = new HrNeedRequest();
            hrNeedRequest.setId(requestDTO.getHrNeedRequestId());
            model.setHrNeedRequest(hrNeedRequest);
        }

        model.setInternalRecruitment(requestDTO.getInternalRecruitment());
        model.setExternalRecruitment(requestDTO.getExternalRecruitment());
        model.setIsAll(requestDTO.getAll());
    }
}
