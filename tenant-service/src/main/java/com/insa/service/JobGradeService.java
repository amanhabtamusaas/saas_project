package com.insa.service;

import com.insa.dto.requestDto.JobGradeRequestDto;
import com.insa.dto.requestDto.JobGradeRequestDto;
import com.insa.dto.responseDto.JobGradeResponseDto;
import com.insa.dto.responseDto.JobGradeResponseDto;
import com.insa.entity.JobGrade;
import com.insa.entity.Tenant;
import com.insa.entity.JobRegistration;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.JobGradeMapper;
import com.insa.repository.JobGradeRepository;
import com.insa.repository.TenantRepository;
import com.insa.repository.JobRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobGradeService {

    private final JobGradeRepository jobGradeRepository;
    private final JobGradeMapper jobGradeMapper;
    private final TenantRepository tenantRepository;
    private final JobRegistrationRepository jobRegistrationRepository;

    public JobGradeResponseDto createJobGrade(Long tenantId, JobGradeRequestDto jobGradeRequestDto) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));
        if (jobGradeRepository.existsByJobGradeNameAndTenantId(jobGradeRequestDto.getJobGradeName(),tenant.getId())) {
            throw new ResourceExistsException("Field Of Study with Name " + jobGradeRequestDto.getJobGradeName() + " already exists");
        }


        JobGrade jobGrade = jobGradeMapper.mapToEntity(jobGradeRequestDto);
        jobGrade.setTenant(tenant);


        jobGrade = jobGradeRepository.save(jobGrade);

        return jobGradeMapper.mapToDto(jobGrade);
    }

    public List<JobGradeResponseDto> getAllJobGrades(Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        List<JobGrade> jobGrades = jobGradeRepository.findAll();
        return jobGrades.stream()
                .filter(jobGrade -> jobGrade.getTenant().getId().equals(tenant.getId()))
                .map(jobGradeMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public JobGradeResponseDto getJobGradeById(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobGrade jobGrade = jobGradeRepository.findById(id)
                .filter(jg -> jg.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobGrade not found with id: " + id + " for the specified tenant"));

        return jobGradeMapper.mapToDto(jobGrade);
    }

    public JobGradeResponseDto updateJobGrade(Long id, Long tenantId, JobGradeRequestDto jobGradeRequestDto) {
        Tenant tenant = getTenantById(tenantId);
//        JobRegistration jobRegistration = getJobRegistrationById(jobRegistrationId);

        JobGrade jobGrade = jobGradeRepository.findById(id)
                .filter(jg -> jg.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobGrade not found with id: " + id + " for the specified tenant"));

        jobGrade = jobGradeMapper.updateJobGrade(jobGrade, jobGradeRequestDto);
//        jobGrade.setJobRegistration(jobRegistration);

        jobGrade = jobGradeRepository.save(jobGrade);

        return jobGradeMapper.mapToDto(jobGrade);
    }

    public void deleteJobGrade(Long id, Long tenantId) {
        Tenant tenant = getTenantById(tenantId);

        JobGrade jobGrade = jobGradeRepository.findById(id)
                .filter(jg -> jg.getTenant().getId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "JobGrade not found with id: " + id + " for the specified tenant"));

        jobGradeRepository.delete(jobGrade);
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
