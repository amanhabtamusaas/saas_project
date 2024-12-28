package com.organization_service.service;

import com.organization_service.dto.requestDto.JobGradeRequestDto;
import com.organization_service.dto.responseDto.JobGradeResponseDto;
import com.organization_service.model.JobGrade;
import com.organization_service.model.JobRegistration;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.JobGradeMapper;
import com.organization_service.repository.JobGradeRepository;
import com.organization_service.repository.TenantRepository;
import com.organization_service.repository.JobRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobGradeService {

    private final JobGradeRepository jobGradeRepository;
    private final JobGradeMapper jobGradeMapper;
    private final TenantRepository tenantRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    public JobGradeResponseDto createJobGrade(UUID tenantId, JobGradeRequestDto jobGradeRequestDto) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        if (jobGradeRepository.existsByJobGradeNameAndTenantId(
                jobGradeRequestDto.getJobGradeName(), tenant.getId())) {
            throw new ResourceExistsException("Job Grade with Name " +
                    jobGradeRequestDto.getJobGradeName() + " already exists");
        }

        JobGrade jobGrade = jobGradeMapper.mapToEntity(jobGradeRequestDto);
        jobGrade.setTenant(tenant);
        jobGrade = jobGradeRepository.save(jobGrade);

        return jobGradeMapper.mapToDto(jobGrade);
    }

    public List<JobGradeResponseDto> getAllJobGrades(UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<JobGrade> jobGrades = jobGradeRepository.findAll();
        return jobGrades.stream()
                .filter(jobGrade -> jobGrade.getTenant().getId().equals(tenant.getId()))
                .map(jobGradeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public JobGradeResponseDto getJobGradeById(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobGrade jobGrade = jobGradeRepository.findById(id)
                .filter(jg -> jg.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobGrade not found with id: " + id + " for the specified tenant"));

        return jobGradeMapper.mapToDto(jobGrade);
    }

    public JobGradeResponseDto updateJobGrade(UUID id, UUID tenantId, JobGradeRequestDto jobGradeRequestDto) {
        Tenant tenant = getTenantById(tenantId);

        JobGrade jobGrade = jobGradeRepository.findById(id)
                .filter(jg -> jg.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobGrade not found with id: " + id + " for the specified tenant"));

        jobGrade = jobGradeMapper.updateJobGrade(jobGrade, jobGradeRequestDto);
        jobGrade = jobGradeRepository.save(jobGrade);

        return jobGradeMapper.mapToDto(jobGrade);
    }

    public void deleteJobGrade(UUID id, UUID tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobGrade jobGrade = jobGradeRepository.findById(id)
                .filter(jg -> jg.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobGrade not found with id: " + id + " for the specified tenant"));

        jobGradeRepository.delete(jobGrade);
    }

    private Tenant getTenantById(UUID tenantId) {
        return tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found with id: " + tenantId));
    }

    private JobRegistration getJobRegistrationById(UUID jobRegistrationId) {
        return jobRegistrationRepository.findById(jobRegistrationId)
                .orElseThrow(() -> new ResourceNotFoundException("JobRegistration not found with id: " + jobRegistrationId));
    }
}