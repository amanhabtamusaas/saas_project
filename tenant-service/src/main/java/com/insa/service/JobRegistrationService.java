package com.insa.service;

import com.insa.dto.requestDto.JobRegistrationRequest;
import com.insa.dto.responseDto.JobRegistrationResponse;
import com.insa.entity.Department;
import com.insa.entity.JobRegistration;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.JobRegistrationMapper;
import com.insa.repository.DepartmentRepository;
import com.insa.repository.JobRegistrationRepository;
import com.insa.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  JobRegistrationService {

    private final JobRegistrationRepository jobRegistrationRepository;
    private final JobRegistrationMapper jobRegistrationMapper;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;

    public JobRegistrationResponse registerJob(Long tenantId,
                                               JobRegistrationRequest jobRegistrationRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        if (jobRegistrationRepository.existsByJobTitleAndTenantId(jobRegistrationRequest.getJobTitle(),tenant.getId())) {
            throw new ResourceExistsException("Field Of Study with Name " + jobRegistrationRequest.getJobTitle() + " already exists");
        }

        if (jobRegistrationRepository.existsByJobCode(jobRegistrationRequest.getJobCode())) {
            throw new ResourceExistsException("Field Of Study with Name " + jobRegistrationRequest.getJobCode() + " already exists");
        }

        JobRegistration jobRegistration = jobRegistrationMapper
                .mapToEntity (jobRegistrationRequest);
        jobRegistration.setTenant (tenant);
        jobRegistration = jobRegistrationRepository.save (jobRegistration);
        return jobRegistrationMapper.mapToDto (jobRegistration);
    }

    public List<JobRegistrationResponse> getAllJobs(Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        List<JobRegistration> jobRegistrations = jobRegistrationRepository.findAll ();
//
        return jobRegistrations.stream()
                .filter(job -> job.getTenant().getId().equals(tenant.getId ()))
//                .filter(job -> job.getDepartment().getId().equals(department.getId ()))
                .map (jobRegistrationMapper::mapToDto)
                .collect(Collectors.toList());
    }
    public List<JobRegistrationResponse> getAllJobsByDepartment(Long tenantId, Long departmentId) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Department not found with id: " + departmentId));

        List<JobRegistration> jobRegistrations = jobRegistrationRepository.findByTenantAndDepartment(tenant, department);

        return jobRegistrations.stream()
                .filter(job -> job.getDepartment().getId().equals(department.getId ()))
                .map(jobRegistrationMapper::mapToDto)
                .collect(Collectors.toList());
    }


    public JobRegistrationResponse getJobById(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        JobRegistration jobRegistration = jobRegistrationRepository.findById (id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Job not found with id: " + id + " for the specified tenant "));
        return jobRegistrationMapper.mapToDto (jobRegistration);
    }

    public JobRegistrationResponse updateJobs(
            Long id, Long tenantId, JobRegistrationRequest jobRegistrationRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        JobRegistration jobRegistration = jobRegistrationRepository.findById (id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Job not found with id: " + id + " for the specified tenant "));
        jobRegistration = jobRegistrationMapper.updateJobRegistration (
                jobRegistration, jobRegistrationRequest);
        jobRegistration = jobRegistrationRepository.save (jobRegistration);
        return jobRegistrationMapper.mapToDto (jobRegistration);
    }

    public void deleteJob(Long id, Long tenantId) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        JobRegistration jobRegistration = jobRegistrationRepository.findById (id)
                .filter(job -> job.getTenant().getId().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Job not found with id: " + id + " for the specified tenant "));
        jobRegistrationRepository.delete (jobRegistration);
    }
}
