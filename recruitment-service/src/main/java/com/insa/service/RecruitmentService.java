package com.insa.service;

import com.insa.dto.apiDto.DepartmentDto;
import com.insa.dto.apiDto.EmployeeDto;
import com.insa.dto.apiDto.JobDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.RecruitmentApproveRequest;
import com.insa.dto.request.RecruitmentRequest;
import com.insa.dto.response.RecruitmentResponse;
import com.insa.entity.Recruitment;
import com.insa.enums.RecruitmentStatus;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.RecruitmentMapper;
import com.insa.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final EmployeeServiceClient employeeServiceClient;
    private final RecruitmentMapper recruitmentMapper;

    @Transactional
    public RecruitmentResponse createRecruitment(Long tenantId,
                                                 RecruitmentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        EmployeeDto requester = employeeServiceClient
                .getEmployeeByEmployeeId(tenant.getId(), request.getRequesterEmployeeId());
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId());
        JobDto job = tenantServiceClient.getJobById(request.getJobId(), tenant.getId());
        Recruitment recruitment = recruitmentMapper.mapToEntity(request);
        recruitment.setTenantId(tenant.getId());
        recruitment.setRequesterEmployeeId(requester.getId());
        recruitment.setDepartmentId(department.getId());
        recruitment.setJobId(job.getId());
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    public List<RecruitmentResponse> getAllRecruitments(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<Recruitment> recruitments = recruitmentRepository.findAll();
        return recruitments.stream()
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .map(recruitmentMapper::mapToDto)
                .toList();
    }

    public RecruitmentResponse getRecruitmentById(Long tenantId,
                                                  Long recruitmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        return recruitmentMapper.mapToDto(recruitment);
    }

    public RecruitmentResponse getRecruitmentByVacancyNumber(Long tenantId,
                                                             String vacancyNumber) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findByVacancyNumber(vacancyNumber)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with vacancy number '" + vacancyNumber + "' in the specified tenant"));
        return recruitmentMapper.mapToDto(recruitment);
    }

    public List<RecruitmentResponse> getRecruitmentByStatus(Long tenantId,
                                                            RecruitmentStatus recruitmentStatus) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<Recruitment> recruitments = recruitmentRepository
                .findByRecruitmentStatus(recruitmentStatus);
        return recruitments.stream()
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .map(recruitmentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public RecruitmentResponse updateRecruitment(Long tenantId,
                                                 Long recruitmentId,
                                                 RecruitmentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        EmployeeDto requester = employeeServiceClient
                .getEmployeeByEmployeeId(tenant.getId(), request.getRequesterEmployeeId());
        DepartmentDto department = tenantServiceClient
                .getDepartmentById(request.getDepartmentId(), tenant.getId());
        JobDto job = tenantServiceClient.getJobById(request.getJobId(), tenant.getId());
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.PENDING)) {
            throw new IllegalStateException(
                    "Recruitment request with id '" + recruitment.getId() + "' is already approved or rejected");
        }
        recruitment = recruitmentMapper.updateRecruitment(recruitment, request);
        if (request.getDepartmentId() != null) {
            recruitment.setDepartmentId(department.getId());
        }
        if (request.getJobId() != null) {
            recruitment.setJobId(job.getId());
        }
        if (request.getRequesterEmployeeId() != null) {
            recruitment.setRequesterEmployeeId(requester.getId());
        }
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    @Transactional
    public RecruitmentResponse approveRecruitment(Long tenantId,
                                                  Long recruitmentId,
                                                  RecruitmentApproveRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        recruitment = recruitmentMapper.approveRecruitment(recruitment, request);
        recruitment = recruitmentRepository.save(recruitment);
        return recruitmentMapper.mapToDto(recruitment);
    }

    @Transactional
    public void deleteRecruitment(Long tenantId,
                                  Long recruitmentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(recruitmentId)
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + recruitmentId + "' in the specified tenant"));
        recruitmentRepository.delete(recruitment);
    }
}
