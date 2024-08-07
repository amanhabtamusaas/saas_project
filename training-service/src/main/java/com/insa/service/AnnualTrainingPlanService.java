package com.insa.service;

import com.insa.dto.apiDto.BudgetYearDto;
import com.insa.dto.apiDto.DepartmentDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.AnnualTrainingPlanRequest;
import com.insa.dto.response.AnnualTrainingPlanResponse;
import com.insa.entity.AnnualTrainingPlan;
import com.insa.entity.TrainingCourseCategory;
import com.insa.entity.TrainingCourse;
import com.insa.entity.TrainingInstitution;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.AnnualTrainingPlanMapper;
import com.insa.repository.AnnualTrainingPlanRepository;
import com.insa.repository.TrainingCourseCategoryRepository;
import com.insa.repository.TrainingCourseRepository;
import com.insa.repository.TrainingInstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnualTrainingPlanService {

    private final AnnualTrainingPlanRepository annualTrainingPlanRepository;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final TrainingCourseCategoryRepository trainingCourseCategoryRepository;
    private final TrainingCourseRepository trainingCourseRepository;
    private final TrainingInstitutionRepository trainingInstitutionRepository;
    private final AnnualTrainingPlanMapper annualTrainingPlanMapper;

    @Transactional
    public AnnualTrainingPlanResponse addAnnualTrainingPlan(Long tenantId,
                                                            AnnualTrainingPlanRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId());
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingInstitution trainingInstitution = trainingInstitutionRepository
                .findById(request.getTrainingInstitutionId())
                .filter(institution -> institution.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + request.getTrainingInstitutionId() +
                                "' in the specified tenant"));
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository
                .findById(request.getCourseCategoryId())
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + request.getCourseCategoryId() +
                                "' in the specified tenant"));
        TrainingCourse trainingCourse = trainingCourseRepository
                .findById(request.getTrainingCourseId())
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .filter(course -> course.getTrainingCourseCategory().equals(trainingCourseCategory))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training course not found with id '" + request.getTrainingCourseId() +
                                "' in the specified tenant"));
        if (annualTrainingPlanRepository.existsByTenantIdAndDepartmentIdAndBudgetYearIdAndTrainingCourseId(
                tenant.getId(), department.getId(), budgetYear.getId(), trainingCourse.getId())) {
            throw new IllegalStateException(
                    "Training plan for course with id '" + trainingCourse.getId() + "' already exists");
        }
        AnnualTrainingPlan annualTrainingPlan = annualTrainingPlanMapper.mapToEntity(request);
        annualTrainingPlan.setTenantId(tenant.getId());
        annualTrainingPlan.setDepartmentId(department.getId());
        annualTrainingPlan.setBudgetYearId(budgetYear.getId());
        annualTrainingPlan.setTrainingInstitution(trainingInstitution);
        annualTrainingPlan.setTrainingCourseCategory(trainingCourseCategory);
        annualTrainingPlan.setTrainingCourse(trainingCourse);
        annualTrainingPlan = annualTrainingPlanRepository.save(annualTrainingPlan);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    public List<AnnualTrainingPlanResponse> getAllAnnualTrainingPlans(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<AnnualTrainingPlan> annualTrainingPlans = annualTrainingPlanRepository.findAll();
        return annualTrainingPlans.stream()
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .map(annualTrainingPlanMapper::mapToDto)
                .toList();
    }

    public AnnualTrainingPlanResponse getAnnualTrainingPlanById(Long tenantId,
                                                                Long trainingPlanId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = annualTrainingPlanRepository.findById(trainingPlanId)
                .filter(training -> training.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Annual training plan not found with id '" + trainingPlanId + "' in the specified tenant"));
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    public List<AnnualTrainingPlanResponse> getAnnualTrainingPlansByDepartment(Long tenantId,
                                                                               Long departmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<AnnualTrainingPlan> annualTrainingPlans = annualTrainingPlanRepository.findAll();
        return annualTrainingPlans.stream()
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .filter(trainingPlan -> trainingPlan.getDepartmentId().equals(departmentId))
                .map(annualTrainingPlanMapper::mapToDto)
                .toList();
    }

    @Transactional
    public AnnualTrainingPlanResponse updateAnnualTrainingPlan(Long tenantId,
                                                               Long trainingPlanId,
                                                               AnnualTrainingPlanRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId());
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        TrainingInstitution trainingInstitution = trainingInstitutionRepository
                .findById(request.getTrainingInstitutionId())
                .filter(institution -> institution.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training institution not found with id '" + request.getTrainingInstitutionId() +
                                "' in the specified tenant"));
        TrainingCourseCategory trainingCourseCategory = trainingCourseCategoryRepository
                .findById(request.getCourseCategoryId())
                .filter(category -> category.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course category not found with id '" + request.getCourseCategoryId() +
                                "' in the specified tenant"));
        TrainingCourse trainingCourse = trainingCourseRepository
                .findById(request.getTrainingCourseId())
                .filter(course -> course.getTenantId().equals(tenant.getId()))
                .filter(course -> course.getTrainingCourseCategory().equals(trainingCourseCategory))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Training course not found with id '" + request.getTrainingCourseId() +
                                "' in the specified tenant"));
        AnnualTrainingPlan annualTrainingPlan = annualTrainingPlanRepository.findById(trainingPlanId)
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Annual training plan not found with id '" + trainingPlanId + "' in the specified tenant"));
        annualTrainingPlan = annualTrainingPlanMapper.updateEntity(annualTrainingPlan, request);
        if (request.getDepartmentId() != null) {
            annualTrainingPlan.setDepartmentId(department.getId());
        }
        if (request.getBudgetYearId() != null) {
            annualTrainingPlan.setBudgetYearId(budgetYear.getId());
        }
        if (request.getTrainingInstitutionId() != null) {
            annualTrainingPlan.setTrainingInstitution(trainingInstitution);
        }
        if (request.getCourseCategoryId() != null) {
            annualTrainingPlan.setTrainingCourseCategory(trainingCourseCategory);
        }
        if (request.getTrainingCourseId() != null) {
            annualTrainingPlan.setTrainingCourse(trainingCourse);
        }
        annualTrainingPlan = annualTrainingPlanRepository.save(annualTrainingPlan);
        return annualTrainingPlanMapper.mapToDto(annualTrainingPlan);
    }

    @Transactional
    public void deleteAnnualTrainingPlan(Long tenantId,
                                         Long trainingPlanId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        AnnualTrainingPlan annualTrainingPlan = annualTrainingPlanRepository.findById(trainingPlanId)
                .filter(trainingPlan -> trainingPlan.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Annual training plan not found with id '" + trainingPlanId + "' in the specified tenant"));
        annualTrainingPlanRepository.delete(annualTrainingPlan);
    }
}
