package com.hr_planning_service.service;

import com.hr_planning_service.client.LeaveServiceClient;
import com.hr_planning_service.client.OrganizationServiceClient;
import com.hr_planning_service.dto.*;
//import com.hr_planning_service.dto.request.HrAnalysisRequestDTO;
import com.hr_planning_service.dto.request.HrAnalysisRequestDto;
//import com.hr_planning_service.dto.response.HrAnalysisResponseDTO;
import com.hr_planning_service.dto.response.HrAnalysisResponseDto;
import com.hr_planning_service.model.HrAnalysis;
import com.hr_planning_service.exception.ResourceNotFoundException;
import com.hr_planning_service.mapper.HrAnalysisMapper;
import com.hr_planning_service.repository.HrAnalysisRepository;
import com.hr_planning_service.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HrAnalysisService {

    private final HrAnalysisRepository hrAnalysisRepository;
    private final HrAnalysisMapper hrAnalysisMapper;
    private final OrganizationServiceClient organizationServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final NeedRequestRepository needRequestRepository;

    public HrAnalysisResponseDto createHrAnalysis(UUID tenantId, HrAnalysisRequestDto hrAnalysisRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (hrAnalysisRequest == null || hrAnalysisRequest.getBudgetYearId() == null
                || hrAnalysisRequest.getWorkUnitId() == null
                || hrAnalysisRequest.getJobRegistrationId() == null) {
            throw new IllegalArgumentException("HrAnalysisRequest and its fields must not be null");
        }

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, hrAnalysisRequest.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(hrAnalysisRequest.getWorkUnitId(), tenantId);
        JobRegistrationDto jobRegistration = organizationServiceClient.getJobById(hrAnalysisRequest.getJobRegistrationId(), tenantId);
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);

        HrAnalysis hrAnalysis = hrAnalysisMapper.mapToEntity(hrAnalysisRequest);

        hrAnalysis.setTenantId(tenant.getId());
        hrAnalysis.setBudgetYearId(budgetYear.getId());
        hrAnalysis.setWorkUnitId(workUnit.getId());
        hrAnalysis.setJobRegistrationId(jobRegistration.getId());

        HrAnalysis savedHrAnalysis = hrAnalysisRepository.save(hrAnalysis);
        return hrAnalysisMapper.mapToDto(savedHrAnalysis);
    }

    public List<HrAnalysisResponseDto> getAllHrAnalyses(UUID tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        List<HrAnalysis> hrAnalyses = hrAnalysisRepository.findAll();
        return hrAnalyses.stream()
                .map(hrAnalysisMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HrAnalysisResponseDto getHrAnalysisById(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));
        return hrAnalysisMapper.mapToDto(hrAnalysis);
    }

    public HrAnalysisResponseDto updateHrAnalysis(UUID tenantId, UUID id, HrAnalysisRequestDto hrAnalysisRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (hrAnalysisRequest == null || hrAnalysisRequest.getBudgetYearId() == null
                || hrAnalysisRequest.getWorkUnitId() == null
                || hrAnalysisRequest.getJobRegistrationId() == null) {
            throw new IllegalArgumentException("HrAnalysisRequest and its fields must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, hrAnalysisRequest.getBudgetYearId());
        WorkUnitDto workUnit = organizationServiceClient.getWorkUnitById(hrAnalysisRequest.getWorkUnitId(), tenantId);
        JobRegistrationDto jobRegistration = organizationServiceClient.getJobById(hrAnalysisRequest.getJobRegistrationId(), tenantId);

        hrAnalysisMapper.updateHrAnalysis(hrAnalysis, hrAnalysisRequest);

        hrAnalysis.setBudgetYearId(budgetYear.getId());
        hrAnalysis.setWorkUnitId(workUnit.getId());
        hrAnalysis.setJobRegistrationId(jobRegistration.getId());

        HrAnalysis updatedHrAnalysis = hrAnalysisRepository.save(hrAnalysis);
        return hrAnalysisMapper.mapToDto(updatedHrAnalysis);
    }

    public void deleteHrAnalysis(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));
        hrAnalysisRepository.delete(hrAnalysis);
    }
}
