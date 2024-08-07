package com.insa.mapper;

import com.insa.entity.JobCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.insa.dto.requestDto.JobCategoryRequest;
import com.insa.dto.responseDto.JobCategoryResponse;
import com.insa.entity.JobCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobCategoryMapper {
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private JobRegistrationMapper jobRegistrationMapper;

    public JobCategory mapToEntity(JobCategoryRequest jobCategoryRequest) {
        JobCategory jobCategory = new JobCategory();
        if (jobCategoryRequest != null) {
            jobCategory.setJobCategoryName(jobCategoryRequest.getJobCategoryName());
            jobCategory.setDescription(jobCategoryRequest.getDescription());
            // Assuming tenant mapping is required
//            jobCategory.setTenant(tenantMapper.mapToEntity(jobCategoryRequest.getTenant()));
//            // Assuming job registration mapping is required
//            jobCategory.setJobRegistration(jobRegistrationMapper.mapToEntity(jobCategoryRequest.getJobRegistration()));
        }
        return jobCategory;
    }

    public JobCategoryResponse mapToDto(JobCategory jobCategory) {
        JobCategoryResponse response = new JobCategoryResponse();
        if (jobCategory != null) {
            response.setId(jobCategory.getId());
            response.setJobCategoryName(jobCategory.getJobCategoryName());
            response.setDescription(jobCategory.getDescription());
            // Assuming tenant ID mapping is required
            response.setTenantId(jobCategory.getTenant().getId());
            // Assuming job registration ID mapping is required
//            response.setJobRegistrationId(jobCategory.getJobRegistration().getId());
        }
        return response;
    }

    public JobCategory updateJobCategory(JobCategory jobCategory, JobCategoryRequest jobCategoryRequest) {
        if (jobCategory != null && jobCategoryRequest != null) {
            jobCategory.setJobCategoryName(jobCategoryRequest.getJobCategoryName());
            jobCategory.setDescription(jobCategoryRequest.getDescription());
            // Assuming tenant mapping is required
//            jobCategory.setTenantId(tenantMapper.mapToEntity(jobCategoryRequest.getTenant()));
//            // Assuming job registration mapping is required
//            jobCategory.setJobRegistration(jobRegistrationMapper.mapToEntity(jobCategoryRequest.getJobRegistration()));
        }
        return jobCategory;
    }
}

