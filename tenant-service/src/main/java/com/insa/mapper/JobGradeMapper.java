package com.insa.mapper;

import com.insa.dto.requestDto.JobGradeRequestDto;
import com.insa.dto.responseDto.JobGradeResponseDto;
import com.insa.entity.JobGrade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobGradeMapper {
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private JobRegistrationMapper jobRegistrationMapper;

    public JobGrade mapToEntity(JobGradeRequestDto jobGradeRequest) {
        JobGrade jobGrade = new JobGrade();
        if (jobGradeRequest != null) {
            jobGrade.setJobGradeName(jobGradeRequest.getJobGradeName());
            jobGrade.setDescription(jobGradeRequest.getDescription());
//            if (jobGradeRequest.getTenant() != null) {
//                jobGrade.setTenant(tenantMapper.mapToEntity(jobGradeRequest.getTenant()));
//            }
//            if (jobGradeRequest.getJobRegistration() != null) {
//                jobGrade.setJobRegistration(jobRegistrationMapper.mapToEntity(jobGradeRequest.getJobRegistration()));
//            }
        }
        return jobGrade;
    }

    public JobGradeResponseDto mapToDto(JobGrade jobGrade) {
        JobGradeResponseDto response = new JobGradeResponseDto();
        if (jobGrade != null) {
            response.setId(jobGrade.getId());
            response.setJobGradeName(jobGrade.getJobGradeName());
            response.setDescription(jobGrade.getDescription());
            if (jobGrade.getTenant() != null) {
                response.setTenantId(jobGrade.getTenant().getId());
            }
//            if (jobGrade.getJobRegistration() != null) {
//                response.setJobRegistrationId(jobGrade.getJobRegistration().getId());
//            }
        }
        return response;
    }

    public JobGrade updateJobGrade(JobGrade jobGrade, JobGradeRequestDto jobGradeRequest) {
        if (jobGrade != null && jobGradeRequest != null) {
            jobGrade.setJobGradeName(jobGradeRequest.getJobGradeName());
            jobGrade.setDescription(jobGradeRequest.getDescription());
//            if (jobGradeRequest.getTenant() != null) {
//                jobGrade.setTenant(tenantMapper.mapToEntity(jobGradeRequest.getTenant()));
//            }
//            if (jobGradeRequest.getJobRegistration() != null) {
//                jobGrade.setJobRegistration(jobRegistrationMapper.mapToEntity(jobGradeRequest.getJobRegistration()));
//            }
        }
        return jobGrade;
    }
}
