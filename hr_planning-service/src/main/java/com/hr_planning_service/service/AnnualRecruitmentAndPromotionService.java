package com.hr_planning_service.service;

import com.hr_planning_service.client.LeaveServiceClient;
import com.hr_planning_service.client.OrganizationServiceClient;
import com.hr_planning_service.dto.BudgetYearDto;
import com.hr_planning_service.dto.TenantDto;
import com.hr_planning_service.dto.WorkUnitDto;
import com.hr_planning_service.dto.request.AnnualRecruitmentAndPromotionRequest;
import com.hr_planning_service.dto.response.AnnualRecruitmentAndPromotionResponse;
import com.hr_planning_service.model.AnnualRecruitmentAndPromotion;
import com.hr_planning_service.model.HrNeedRequest;
import com.hr_planning_service.exception.ResourceNotFoundException;
import com.hr_planning_service.mapper.AnnualRecruitmentAndPromotionMapper;
import com.hr_planning_service.repository.AnnualRecruitmentAndPromotionRepository;
import com.hr_planning_service.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnualRecruitmentAndPromotionService {

    private final AnnualRecruitmentAndPromotionRepository repository;
    private final NeedRequestRepository needRequestRepository;
    private final AnnualRecruitmentAndPromotionMapper mapper;
    private final OrganizationServiceClient organizationServiceClient;
    private final LeaveServiceClient leaveServiceClient;

    public AnnualRecruitmentAndPromotionResponse createAnnualRecruitmentAndPromotion(UUID tenantId, AnnualRecruitmentAndPromotionRequest request) {
        validateInputs(tenantId, request);

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, request.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(request.getWorkUnitId(), tenantId);
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        HrNeedRequest hrNeedRequest = fetchHrNeedRequest(request.getHrNeedRequestId());

        AnnualRecruitmentAndPromotion model = mapper.mapToEntity(request);
        model.setHrNeedRequest(hrNeedRequest);
        model.setTenantId(tenant.getId());
        model.setProcessedDate(LocalDate.now()); // Set current date as processed date

        AnnualRecruitmentAndPromotion savedEntity = repository.save(model);
        return mapper.toDto(savedEntity);
    }

    public List<AnnualRecruitmentAndPromotionResponse> getAllAnnualRecruitmentAndPromotions(UUID tenantId) {
        validateTenantId(tenantId);

        List<AnnualRecruitmentAndPromotion> entities = repository.findAll();
        return entities.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public AnnualRecruitmentAndPromotionResponse getAnnualRecruitmentAndPromotionById(UUID tenantId, UUID id) {
        validateTenantId(tenantId);

        AnnualRecruitmentAndPromotion model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));
        return mapper.toDto(model);
    }

    public AnnualRecruitmentAndPromotionResponse updateAnnualRecruitmentAndPromotion(UUID tenantId, UUID id, AnnualRecruitmentAndPromotionRequest request) {
        validateInputs(tenantId, request);

        AnnualRecruitmentAndPromotion model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, request.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(request.getWorkUnitId(), tenantId);

        HrNeedRequest hrNeedRequest = fetchHrNeedRequest(request.getHrNeedRequestId());

        mapper.updateEntity(model, request);
        model.setHrNeedRequest(hrNeedRequest);
        model.setTenantId(tenantId); // Assuming tenantId remains unchanged in updates

        AnnualRecruitmentAndPromotion updatedEntity = repository.save(model);
        return mapper.toDto(updatedEntity);
    }

    public void deleteAnnualRecruitmentAndPromotion(UUID tenantId, UUID id) {
        validateTenantId(tenantId);

        AnnualRecruitmentAndPromotion model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnnualRecruitmentAndPromotion not found with id " + id));
        repository.delete(model);
    }

    private void validateInputs(UUID tenantId, AnnualRecruitmentAndPromotionRequest request) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (request == null || request.getBudgetYearId() == null
                || request.getWorkUnitId() == null
                || request.getHrNeedRequestId() == null) {
            throw new IllegalArgumentException("AnnualRecruitmentAndPromotionRequest and its fields must not be null");
        }
    }

    private void validateTenantId(UUID tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }
    }

    private HrNeedRequest fetchHrNeedRequest(UUID hrNeedRequestId) {
        return needRequestRepository.findById(hrNeedRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("HrNeedRequest not found with id " + hrNeedRequestId));
    }
}
