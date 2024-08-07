package com.insa.hr_planning_service.service;

import com.insa.hr_planning_service.dto.BudgetYearDto;
import com.insa.hr_planning_service.dto.TenantDto;
import com.insa.hr_planning_service.dto.WorkUnitDto;
import com.insa.hr_planning_service.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.insa.hr_planning_service.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.insa.hr_planning_service.entity.AnnualRecruitmentAndPromotion;
import com.insa.hr_planning_service.entity.HrNeedRequest;
import com.insa.hr_planning_service.exception.ResourceNotFoundException;
import com.insa.hr_planning_service.mapper.AnnualRecruitmentAndPromotionMapper;
import com.insa.hr_planning_service.repository.AnnualRecruitmentAndPromotionRepository;
import com.insa.hr_planning_service.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnualRecruitmentAndPromotionService {

    private final AnnualRecruitmentAndPromotionRepository repository;
    private final NeedRequestRepository needRequestRepository;
    private final AnnualRecruitmentAndPromotionMapper mapper;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;

    public AnnualRecruitmentAndPromotionResponse createAnnualRecruitmentAndPromotion(Long tenantId, AnnualRecruitmentAndPromotionRequest request) {
        validateInputs(tenantId, request);

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, request.getBudgetYearId());
        WorkUnitDto workUnit = tenantServiceClient.getWorkUnitById(request.getWorkUnitId(), tenantId);
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        HrNeedRequest hrNeedRequest = fetchHrNeedRequest(request.getHrNeedRequestId());

        AnnualRecruitmentAndPromotion entity = mapper.toEntity(request);
        entity.setHrNeedRequest(hrNeedRequest);
        entity.setTenantId(tenant.getId());
        entity.setProcessedDate(LocalDate.now()); // Set current date as processed date

        AnnualRecruitmentAndPromotion savedEntity = repository.save(entity);
        return mapper.toDto(savedEntity);
    }

    public List<AnnualRecruitmentAndPromotionResponse> getAllAnnualRecruitmentAndPromotions(Long tenantId) {
        validateTenantId(tenantId);

        List<AnnualRecruitmentAndPromotion> entities = repository.findAll();
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public AnnualRecruitmentAndPromotionResponse getAnnualRecruitmentAndPromotionById(Long tenantId, Long id) {
        validateTenantId(tenantId);

        AnnualRecruitmentAndPromotion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));
        return mapper.toDto(entity);
    }

    public AnnualRecruitmentAndPromotionResponse updateAnnualRecruitmentAndPromotion(Long tenantId, Long id, AnnualRecruitmentAndPromotionRequest request) {
        validateInputs(tenantId, request);

        AnnualRecruitmentAndPromotion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, request.getBudgetYearId());
        WorkUnitDto workUnit = tenantServiceClient.getWorkUnitById(request.getWorkUnitId(), tenantId);

        HrNeedRequest hrNeedRequest = fetchHrNeedRequest(request.getHrNeedRequestId());

        mapper.updateEntity(entity, request);
        entity.setHrNeedRequest(hrNeedRequest);
        entity.setTenantId(tenantId); // Assuming tenantId remains unchanged in updates

        AnnualRecruitmentAndPromotion updatedEntity = repository.save(entity);
        return mapper.toDto(updatedEntity);
    }

    public void deleteAnnualRecruitmentAndPromotion(Long tenantId, Long id) {
        validateTenantId(tenantId);

        AnnualRecruitmentAndPromotion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));
        repository.delete(entity);
    }

    private void validateInputs(Long tenantId, AnnualRecruitmentAndPromotionRequest request) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (request == null || request.getBudgetYearId() == null
                || request.getWorkUnitId() == null
                || request.getHrNeedRequestId() == null) {
            throw new IllegalArgumentException("AnnualRecruitmentAndPromotionRequest and its fields must not be null");
        }
    }

    private void validateTenantId(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
    }

    private HrNeedRequest fetchHrNeedRequest(Long hrNeedRequestId) {
        return needRequestRepository.findById(hrNeedRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("HrNeedRequest not found with id " + hrNeedRequestId));
    }
}
