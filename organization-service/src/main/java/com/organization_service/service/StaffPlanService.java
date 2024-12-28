package com.organization_service.service;

import com.organization_service.dto.requestDto.StaffPlanRequest;
import com.organization_service.dto.responseDto.StaffPlanResponse;
import com.organization_service.model.Department;
import com.organization_service.model.StaffPlan;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.StaffPlanMapper;
import com.organization_service.repository.DepartmentRepository;
import com.organization_service.repository.JobRegistrationRepository;
import com.organization_service.repository.StaffPlanRepository;
import com.organization_service.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffPlanService {

    private final StaffPlanRepository staffPlanRepository;
    private final StaffPlanMapper staffPlanMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    private static final Logger logger = LoggerFactory.getLogger(StaffPlanService.class);

    public StaffPlanResponse createStaffPlan(UUID tenantId, StaffPlanRequest staffPlanRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        Optional.ofNullable(staffPlanRequest.getJobRegistrationId()).ifPresent(jobRegistrationId ->
                jobRegistrationRepository.findById(jobRegistrationId)
                        .orElseThrow(() -> new EntityNotFoundException("Job Registration not found with ID: " +
                                jobRegistrationId))
        );

        Optional.ofNullable(staffPlanRequest.getDepartmentId()).ifPresent(departmentId ->
                departmentRepository.findById(departmentId)
                        .orElseThrow(() -> new EntityNotFoundException("Department not found with ID: " +
                                departmentId))
        );

        if (staffPlanRepository.existsByTenantId(tenant.getId())) {
            throw new ResourceExistsException("Staff Plan with tenant " + tenant.getId() + " already exists");
        }

        StaffPlan staffPlan = staffPlanMapper.mapToEntity(staffPlanRequest);
        staffPlan.setTenant(tenant);
        staffPlan = staffPlanRepository.save(staffPlan);
        return staffPlanMapper.mapToDto(staffPlan);
    }

    public List<StaffPlanResponse> getAllStaffPlans(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        List<StaffPlan> staffPlans = staffPlanRepository.findAll();
        return staffPlans.stream()
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .map(staffPlanMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<StaffPlanResponse> getStaffPlanByDepartmentId(UUID departmentId, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        List<StaffPlan> staffPlans = staffPlanRepository.findByDepartmentId(departmentId);

        return staffPlans.stream()
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .map(staffPlanMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public StaffPlanResponse updateStaffPlan(UUID id, UUID tenantId, StaffPlanRequest staffPlanRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        StaffPlan staffPlan = staffPlanRepository.findById(id)
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> {
                    logger.error("Staff-plan not found with id: " + id + " for the specified tenant");
                    return new ResourceNotFoundException("Staff-plan not found with id: " + id +
                            " for the specified tenant");
                });

        staffPlan = staffPlanMapper.updateStaffPlan(staffPlan, staffPlanRequest);
        staffPlan = staffPlanRepository.save(staffPlan);
        return staffPlanMapper.mapToDto(staffPlan);
    }

    public void deleteStaffPlan(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> {
                    logger.error("Tenant not found with id: " + tenantId);
                    return new ResourceNotFoundException("Tenant not found with id: " + tenantId);
                });

        StaffPlan staffPlan = staffPlanRepository.findById(id)
                .filter(staff -> staff.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> {
                    logger.error("Staff-plan not found with id: " + id + " for the specified tenant");
                    return new ResourceNotFoundException("Staff-plan not found with id: " + id + " for the specified tenant");
                });

        staffPlanRepository.delete(staffPlan);
    }
}