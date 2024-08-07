package com.insa.service;

import com.insa.dto.requestDto.JobCategoryRequest;
import com.insa.dto.responseDto.JobCategoryResponse;
import com.insa.entity.JobCategory;
import com.insa.entity.Tenant;
import com.insa.entity.JobRegistration;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.JobCategoryMapper;
import com.insa.repository.JobCategoryRepository;
import com.insa.repository.TenantRepository;
import com.insa.repository.JobRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobCategoryService {

    private final JobCategoryRepository jobCategoryRepository;
    private final JobCategoryMapper jobCategoryMapper;
    private final TenantRepository tenantRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    public JobCategoryResponse createJobCategory(Long tenantId, JobCategoryRequest jobCategoryRequest) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));

        if (jobCategoryRepository.existsByJobCategoryNameAndTenantId(jobCategoryRequest.getJobCategoryName(),tenant.getId())) {
            throw new ResourceExistsException("Education Level with Name " + jobCategoryRequest.getJobCategoryName() + " already exists");
        }


        JobCategory jobCategory = jobCategoryMapper.mapToEntity(jobCategoryRequest);
        jobCategory.setTenant(tenant);


        jobCategory = jobCategoryRepository.save(jobCategory);

        return jobCategoryMapper.mapToDto(jobCategory);
    }

    public List<JobCategoryResponse> getAllJobCategories(Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<JobCategory> jobCategories = jobCategoryRepository.findAll();
        return jobCategories.stream()
                .filter(jobCategory -> jobCategory.getTenant().getId().equals(tenant.getId()))
                .map(jobCategoryMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public JobCategoryResponse getJobCategoryById(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobCategory jobCategory = jobCategoryRepository.findById(id)
                .filter(jc -> jc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCategory not found with id: " + id + " for the specified tenant"));

        return jobCategoryMapper.mapToDto(jobCategory);
    }

    public JobCategoryResponse updateJobCategory(Long id, Long tenantId, JobCategoryRequest jobCategoryRequest) {
        Tenant tenant = getTenantById(tenantId);


        JobCategory jobCategory = jobCategoryRepository.findById(id)
                .filter(jc -> jc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCategory not found with id: " + id + " for the specified tenant"));

        jobCategory = jobCategoryMapper.updateJobCategory(jobCategory, jobCategoryRequest);

        jobCategory = jobCategoryRepository.save(jobCategory);

        return jobCategoryMapper.mapToDto(jobCategory);
    }

    public void deleteJobCategory(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobCategory jobCategory = jobCategoryRepository.findById(id)
                .filter(jc -> jc.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobCategory not found with id: " + id + " for the specified tenant"));

        jobCategoryRepository.delete(jobCategory);
    }

    private Tenant getTenantById(Long tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }

    private JobRegistration getJobRegistrationById(Long jobRegistrationId) {
        return jobRegistrationRepository.findById(jobRegistrationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobRegistration not found with id: " + jobRegistrationId));
    }
}

