package com.insa.hr_planning_service.service;

import com.insa.hr_planning_service.dto.*;
//import com.insa.hr_planning_service.dto.request.HrAnalysisRequestDTO;
import com.insa.hr_planning_service.dto.request.HrAnalysisRequestDto;
//import com.insa.hr_planning_service.dto.response.HrAnalysisResponseDTO;
import com.insa.hr_planning_service.dto.response.HrAnalysisResponseDto;
import com.insa.hr_planning_service.entity.HrAnalysis;
import com.insa.hr_planning_service.exception.ResourceNotFoundException;
import com.insa.hr_planning_service.mapper.HrAnalysisMapper;
import com.insa.hr_planning_service.repository.HrAnalysisRepository;
import com.insa.hr_planning_service.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HrAnalysisService {

    private final HrAnalysisRepository hrAnalysisRepository;
    private final HrAnalysisMapper hrAnalysisMapper;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;
    private final NeedRequestRepository needRequestRepository;

    public HrAnalysisResponseDto createHrAnalysis(Long tenantId, HrAnalysisRequestDto hrAnalysisRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (hrAnalysisRequest == null || hrAnalysisRequest.getBudgetYearId() == null
                || hrAnalysisRequest.getWorkUnitId() == null
                || hrAnalysisRequest.getJobRegistrationId() == null) {
            throw new IllegalArgumentException("HrAnalysisRequest and its fields must not be null");
        }

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, hrAnalysisRequest.getBudgetYearId());
        WorkUnitDto workUnit = tenantServiceClient.getWorkUnitById(hrAnalysisRequest.getWorkUnitId(), tenantId);
        JobRegistrationDto jobRegistration = tenantServiceClient.getJobById(hrAnalysisRequest.getJobRegistrationId(), tenantId);
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        HrAnalysis hrAnalysis = hrAnalysisMapper.mapToEntity(hrAnalysisRequest);

        hrAnalysis.setTenantId(tenant.getId());
        hrAnalysis.setBudgetYearId(budgetYear.getId());
        hrAnalysis.setWorkUnitId(workUnit.getId());
        hrAnalysis.setJobRegistrationId(jobRegistration.getId());

        HrAnalysis savedHrAnalysis = hrAnalysisRepository.save(hrAnalysis);
        return hrAnalysisMapper.mapToDto(savedHrAnalysis);
    }

    public List<HrAnalysisResponseDto> getAllHrAnalyses(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        List<HrAnalysis> hrAnalyses = hrAnalysisRepository.findAll();
        return hrAnalyses.stream()
                .map(hrAnalysisMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public HrAnalysisResponseDto getHrAnalysisById(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));
        return hrAnalysisMapper.mapToDto(hrAnalysis);
    }

    public HrAnalysisResponseDto updateHrAnalysis(Long tenantId, Long id, HrAnalysisRequestDto hrAnalysisRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (hrAnalysisRequest == null || hrAnalysisRequest.getBudgetYearId() == null
                || hrAnalysisRequest.getWorkUnitId() == null
                || hrAnalysisRequest.getJobRegistrationId() == null) {
            throw new IllegalArgumentException("HrAnalysisRequest and its fields must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, hrAnalysisRequest.getBudgetYearId());
        WorkUnitDto workUnit = tenantServiceClient.getWorkUnitById(hrAnalysisRequest.getWorkUnitId(), tenantId);
        JobRegistrationDto jobRegistration = tenantServiceClient.getJobById(hrAnalysisRequest.getJobRegistrationId(), tenantId);

        hrAnalysisMapper.updateHrAnalysis(hrAnalysis, hrAnalysisRequest);

        hrAnalysis.setBudgetYearId(budgetYear.getId());
        hrAnalysis.setWorkUnitId(workUnit.getId());
        hrAnalysis.setJobRegistrationId(jobRegistration.getId());

        HrAnalysis updatedHrAnalysis = hrAnalysisRepository.save(hrAnalysis);
        return hrAnalysisMapper.mapToDto(updatedHrAnalysis);
    }

    public void deleteHrAnalysis(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrAnalysis hrAnalysis = hrAnalysisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HrAnalysis not found with id " + id));
        hrAnalysisRepository.delete(hrAnalysis);
    }
}
