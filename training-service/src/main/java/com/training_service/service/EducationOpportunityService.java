package com.training_service.service;

import com.training_service.dto.clientDto.*;
import com.training_service.dto.request.EducationOpportunityRequest;
import com.training_service.dto.response.EducationOpportunityResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.exception.ResourceNotFoundException;
import com.training_service.model.EducationOpportunity;
import com.training_service.mapper.EducationOpportunityMapper;
import com.training_service.repository.EducationOpportunityRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EducationOpportunityService {

    private final EducationOpportunityRepository educationOpportunityRepository;
    private final EducationOpportunityMapper educationOpportunityMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public EducationOpportunityResponse createEducationOpportunity(UUID tenantId,
                                                                   EducationOpportunityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        BudgetYearDto budgetYear = validationUtil
                .getBudgetYearById(tenant.getId(), request.getBudgetYearId());
        EmployeeDto employee = validationUtil
                .getEmployeeByEmployeeId(tenant.getId(), request.getEmployeeId());
        if (educationOpportunityRepository.existsByTenantIdAndBudgetYearIdAndEmployeeId(
                tenant.getId(), budgetYear.getId(), employee.getId())) {
            throw new ResourceExistsException(
                    "Employee with id '" + employee.getEmployeeId() + "' already been granted an education" +
                            " opportunity for the budget year with id '" + budgetYear.getId() + "'");
        }
        EducationOpportunity educationOpportunity = educationOpportunityMapper
                .mapToEntity(tenant, employee, budgetYear, request);
        educationOpportunity = educationOpportunityRepository.save(educationOpportunity);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    public List<EducationOpportunityResponse> getAllEducationOpportunities(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<EducationOpportunity> educationOpportunities = educationOpportunityRepository.findAll();
        return educationOpportunities.stream()
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .map(educationOpportunityMapper::mapToDto)
                .toList();
    }

    public EducationOpportunityResponse getEducationOpportunityById(UUID tenantId,
                                                                    UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EducationOpportunity educationOpportunity = getOpportunityById(tenant.getId(), educationId);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    public List<EducationOpportunityResponse> getEducationOpportunityByEmployeeId(UUID tenantId,
                                                                                  UUID employeeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<EducationOpportunity> educationOpportunities = educationOpportunityRepository
                .findByTenantIdAndEmployeeId(tenant.getId(), employeeId);
        return educationOpportunities.stream()
                .filter(ed -> ed.getTenantId().equals(tenant.getId()))
                .map(educationOpportunityMapper::mapToDto)
                .toList();
    }

    @Transactional
    public EducationOpportunityResponse updateEducationOpportunity(UUID tenantId,
                                                                   UUID educationId,
                                                                   EducationOpportunityRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EducationOpportunity educationOpportunity = getOpportunityById(tenant.getId(), educationId);
        educationOpportunity = educationOpportunityMapper.updateEntity(tenant, educationOpportunity, request);
        educationOpportunity = educationOpportunityRepository.save(educationOpportunity);
        return educationOpportunityMapper.mapToDto(educationOpportunity);
    }

    @Transactional
    public void deleteEducationOpportunity(UUID tenantId,
                                           UUID educationId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        EducationOpportunity educationOpportunity = getOpportunityById(tenant.getId(), educationId);
        educationOpportunityRepository.delete(educationOpportunity);
    }

    public EducationOpportunity getOpportunityById(UUID tenantId, UUID opportunityId) {

        return educationOpportunityRepository.findById(opportunityId)
                .filter(ed -> ed.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Education opportunity not found with id '" + opportunityId + "'"));
    }
}