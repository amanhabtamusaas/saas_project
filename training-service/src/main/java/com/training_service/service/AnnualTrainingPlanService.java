package com.training_service.service;

import com.training_service.dto.clientDto.BudgetYearDto;
import com.training_service.dto.clientDto.DepartmentDto;
import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.AnnualTrainingPlanRequest;
import com.training_service.dto.response.AnnualTrainingPlanResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.exception.ResourceNotFoundException;
import com.training_service.model.AnnualTrainingPlan;
import com.training_service.model.TrainingCourse;
import com.training_service.mapper.AnnualTrainingPlanMapper;
import com.training_service.repository.AnnualTrainingPlanRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnualTrainingPlanService {

    private final AnnualTrainingPlanRepository annualTrainingPlanRepository;
    private final AnnualTrainingPlanMapper annualTrainingPlanMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public AnnualTrainingPlanResponse addAnnualTrainingPlan(UUID tenantId,
                                                            AnnualTrainingPlanRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        DepartmentDto department = validationUtil
                .getDepartmentById(tenant.getId(), request.getDepartmentId());
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingCourse trainingCourse = validationUtil
                .getTrainingCourseById(tenant.getId(), request.getTrainingCourseId());
        if (annualTrainingPlanRepository.existsByTenantIdAndDepartmentIdAndBudgetYearIdAndTrainingCourseId(
                tenant.getId(), department.getId(), budgetYear.getId(), trainingCourse.getId())) {
            throw new ResourceExistsException(
                    "Training plan for course with id '" + trainingCourse.getId() + "' already exists");
        }
        AnnualTrainingPlan annualTrainingPlan = annualTrainingPlanMapper
                .mapToEntity(tenant, department, trainingCourse, budgetYear, request);
        annualTrainingPlan = annualTrainingPlanRepository.save(annualTrainingPlan);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    public List<AnnualTrainingPlanResponse> getAllAnnualTrainingPlans(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<AnnualTrainingPlan> annualTrainingPlans = annualTrainingPlanRepository.findAll();
        return annualTrainingPlans.stream()
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .map(annualTrainingPlanMapper::mapToDto)
                .toList();
    }

    public AnnualTrainingPlanResponse getAnnualTrainingPlanById(UUID tenantId,
                                                                UUID trainingPlanId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = getTrainingPlanById(tenant.getId(), trainingPlanId);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    public List<AnnualTrainingPlanResponse> getAnnualTrainingPlansByDepartmentId(UUID tenantId,
                                                                                 UUID departmentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<AnnualTrainingPlan> annualTrainingPlans = annualTrainingPlanRepository.findAll();
        return annualTrainingPlans.stream()
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .filter(trainingPlan -> trainingPlan.getDepartmentId().equals(departmentId))
                .map(annualTrainingPlanMapper::mapToDto)
                .toList();
    }

    @Transactional
    public AnnualTrainingPlanResponse updateAnnualTrainingPlan(UUID tenantId,
                                                               UUID trainingPlanId,
                                                               AnnualTrainingPlanRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = getTrainingPlanById(tenant.getId(), trainingPlanId);
        annualTrainingPlan = annualTrainingPlanMapper.updateEntity(tenant, annualTrainingPlan, request);
        annualTrainingPlan = annualTrainingPlanRepository.save(annualTrainingPlan);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    @Transactional
    public void deleteAnnualTrainingPlan(UUID tenantId,
                                         UUID trainingPlanId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = getTrainingPlanById(tenant.getId(), trainingPlanId);
        annualTrainingPlanRepository.delete(annualTrainingPlan);
    }

    private AnnualTrainingPlan getTrainingPlanById(UUID tenantId, UUID trainingPlanId) {

        return annualTrainingPlanRepository.findById(trainingPlanId)
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Annual training plan not found with id '" + trainingPlanId + "'"));
    }
}