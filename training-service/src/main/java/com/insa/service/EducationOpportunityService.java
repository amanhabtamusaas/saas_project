package com.insa.service;

import com.insa.dto.apiDto.*;
import com.insa.dto.request.EducationOpportunityRequest;
import com.insa.dto.response.EducationOpportunityResponse;
import com.insa.entity.EducationOpportunity;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.EducationOpportunityMapper;
import com.insa.repository.EducationOpportunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationOpportunityService {

    private final EducationOpportunityRepository educationOpportunityRepository;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final EducationOpportunityMapper educationOpportunityMapper;

    @Transactional
    public EducationOpportunityResponse createEducationOpportunity(Long tenantId,
                                                                   EducationOpportunityRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        EmployeeDto employee = employeeServiceClient
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        QualificationDto qualification = tenantServiceClient
                .getQualificationById(tenant.getId(), request.getQualificationId());
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());
        if (educationOpportunityRepository.existsByTenantIdAndBudgetYearIdAndEmployeeId(
                tenant.getId(), budgetYear.getId(), employee.getId())) {
            throw new ResourceExistsException(
                    "Employee with id '" + employee.getEmployeeId() +
                            "' already been granted an education opportunity for the budget year with id '" +
                            budgetYear.getId() + "'");
        }
        EducationOpportunity educationOpportunity = educationOpportunityMapper.mapToEntity(request);
        educationOpportunity.setTenantId(tenant.getId());
        educationOpportunity.setBudgetYearId(budgetYear.getId());
        educationOpportunity.setEmployeeId(employee.getId());
        educationOpportunity.setQualificationId(qualification.getId());
        educationOpportunity.setEducationLevelId(educationLevel.getId());
        educationOpportunity = educationOpportunityRepository.save(educationOpportunity);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    public List<EducationOpportunityResponse> getAllEducationOpportunities(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<EducationOpportunity> educationOpportunities = educationOpportunityRepository.findAll();
        return educationOpportunities.stream()
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .map(educationOpportunityMapper::mapToDto)
                .toList();
    }

    public EducationOpportunityResponse getEducationOpportunityById(Long tenantId,
                                                                    Long educationId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        EducationOpportunity educationOpportunity = educationOpportunityRepository.findById(educationId)
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education opportunity not found with id '" + educationId + "' in the specified tenant"));
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    public List<EducationOpportunityResponse> getEducationOpportunityByEmployeeId(Long tenantId,
                                                                                  Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<EducationOpportunity> educationOpportunities = educationOpportunityRepository
                .findByTenantIdAndEmployeeId(tenant.getId(), employeeId);
        return educationOpportunities.stream()
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .map(educationOpportunityMapper::mapToDto)
                .toList();
    }

    @Transactional
    public EducationOpportunityResponse updateEducationOpportunity(Long tenantId,
                                                                   Long educationId,
                                                                   EducationOpportunityRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        BudgetYearDto budgetYear = leaveServiceClient
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        EmployeeDto employee = employeeServiceClient
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        QualificationDto qualification = tenantServiceClient
                .getQualificationById(tenant.getId(), request.getQualificationId());
        EducationLevelDto educationLevel = tenantServiceClient
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());
        EducationOpportunity educationOpportunity = educationOpportunityRepository.findById(educationId)
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education opportunity not found with id '" + educationId + "' in the specified tenant"));
        educationOpportunity = educationOpportunityMapper.updateEntity(educationOpportunity, request);
        if (request.getBudgetYearId() != null) {
            educationOpportunity.setBudgetYearId(budgetYear.getId());
        }
        if (request.getEmployeeId() != null) {
            educationOpportunity.setEmployeeId(employee.getId());
        }
        if (request.getQualificationId() != null) {
            educationOpportunity.setQualificationId(qualification.getId());
        }
        if (request.getEducationLevelId() != null) {
            educationOpportunity.setEducationLevelId(educationLevel.getId());
        }
        educationOpportunity = educationOpportunityRepository.save(educationOpportunity);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    @Transactional
    public void deleteEducationOpportunity(Long tenantId,
                                           Long educationId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        EducationOpportunity educationOpportunity = educationOpportunityRepository.findById(educationId)
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education opportunity not found with id '" + educationId + "' in the specified tenant"));
        educationOpportunityRepository.delete(educationOpportunity);
    }
}
