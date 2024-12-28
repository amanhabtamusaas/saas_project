package com.organization_service.service;

import com.organization_service.dto.requestDto.JobRegistrationRequest;
import com.organization_service.dto.responseDto.JobRegistrationResponse;
import com.organization_service.model.Department;
import com.organization_service.model.JobRegistration;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.JobRegistrationMapper;
import com.organization_service.repository.DepartmentRepository;
import com.organization_service.repository.JobRegistrationRepository;
import com.organization_service.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobRegistrationService {

    private final JobRegistrationRepository jobRegistrationRepository;
    private final JobRegistrationMapper jobRegistrationMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;

    public JobRegistrationResponse registerJob(UUID tenantId, JobRegistrationRequest jobRegistrationRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        if (jobRegistrationRepository.existsByJobTitleAndTenantId(
                jobRegistrationRequest.getJobTitle(), tenant.getId())) {
            throw new ResourceExistsException("Job with Title " +
                    jobRegistrationRequest.getJobTitle() + " already exists");
        }

        if (jobRegistrationRepository.existsByJobCode(jobRegistrationRequest.getJobCode())) {
            throw new ResourceExistsException("Job with Code " +
                    jobRegistrationRequest.getJobCode() + " already exists");
        }

        JobRegistration jobRegistration = jobRegistrationMapper
                .mapToEntity(jobRegistrationRequest);
        jobRegistration.setTenant(tenant);
        jobRegistration = jobRegistrationRepository.save(jobRegistration);

        return jobRegistrationMapper.mapToDto(jobRegistration);
    }

    public List<JobRegistrationResponse> getAllJobs(UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        List<JobRegistration> jobRegistrations = jobRegistrationRepository.findAll();
        return jobRegistrations.stream()
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .map(jobRegistrationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<JobRegistrationResponse> getAllJobsByDepartment(UUID tenantId, UUID departmentId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId));

        List<JobRegistration> jobRegistrations = jobRegistrationRepository
                .findByTenantAndDepartment(tenant, department);

        return jobRegistrations.stream()
                .map(jobRegistrationMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public JobRegistrationResponse getJobById(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        JobRegistration jobRegistration = jobRegistrationRepository.findById(id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job not found with id: " + id + " for the specified tenant"));

        return jobRegistrationMapper.mapToDto(jobRegistration);
    }

    public JobRegistrationResponse updateJobs(UUID id, UUID tenantId, JobRegistrationRequest jobRegistrationRequest) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        JobRegistration jobRegistration = jobRegistrationRepository.findById(id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job not found with id: " + id + " for the specified tenant"));

        jobRegistration = jobRegistrationMapper.updateJobRegistration(jobRegistration, jobRegistrationRequest);
        jobRegistration = jobRegistrationRepository.save(jobRegistration);

        return jobRegistrationMapper.mapToDto(jobRegistration);
    }

    public void deleteJob(UUID id, UUID tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        JobRegistration jobRegistration = jobRegistrationRepository.findById(id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job not found with id: " + id + " for the specified tenant"));

        jobRegistrationRepository.delete(jobRegistration);
    }
}