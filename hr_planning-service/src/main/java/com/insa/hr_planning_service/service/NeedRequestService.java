package com.insa.hr_planning_service.service;


import com.insa.hr_planning_service.dto.*;
import com.insa.hr_planning_service.dto.request.NeedRequestRequestDto;
import com.insa.hr_planning_service.dto.response.NeedRequestResponseDto;
import com.insa.hr_planning_service.entity.HrNeedRequest;
import com.insa.hr_planning_service.exception.ResourceNotFoundException;
import com.insa.hr_planning_service.mapper.NeedRequestMapper;
import com.insa.hr_planning_service.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NeedRequestService {

    private final NeedRequestRepository needRequestRepository;
    private final NeedRequestMapper needRequestMapper;
    private final TenantServiceClient tenantServiceClient;
    private final LeaveServiceClient leaveServiceClient;

    public NeedRequestResponseDto createNeedRequest(Long tenantId, NeedRequestRequestDto needRequestRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (needRequestRequest == null || needRequestRequest.getBudgetYearId() == null
                || needRequestRequest.getDepartmentId() == null
                || needRequestRequest.getStaffPlanId() == null) {
            throw new IllegalArgumentException("NeedRequestRequest and its fields must not be null");
        }

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, needRequestRequest.getBudgetYearId());
        DepartmentDto department = tenantServiceClient.getDepartmentById(needRequestRequest.getDepartmentId(), tenantId);
        StaffPlanDto jobRegistration = tenantServiceClient.getStaffPlanById(needRequestRequest.getStaffPlanId(), tenantId);
        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);

        HrNeedRequest hrNeedRequest = needRequestMapper.mapToEntity(needRequestRequest);

        hrNeedRequest.setTenantId(tenant.getId());
        hrNeedRequest.setBudgetYearId(budgetYear.getId());
        hrNeedRequest.setDepartmentId(department.getId());
        hrNeedRequest.setStaffPlanId(jobRegistration.getId());

//        if (needRequestRepository.existsByNoOfPosition(needRequestRequest.getNoOfPosition())) {
//            throw new ResourceExistsException("NeedRequest with the same number of positions already exists");
//        }
        // Set created_at field with current timestamp
        hrNeedRequest.setCreatedAt(LocalDateTime.now());


        HrNeedRequest savedHrNeedRequest = needRequestRepository.save(hrNeedRequest);
        return needRequestMapper.mapToDto(savedHrNeedRequest);
    }

    public List<NeedRequestResponseDto> getAllNeedRequests(Long tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        List<HrNeedRequest> hrNeedRequests = needRequestRepository.findAll();
        return hrNeedRequests.stream()
                .map(needRequestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public NeedRequestResponseDto getNeedRequestById(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrNeedRequest hrNeedRequest = needRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NeedRequest not found with id " + id));
        return needRequestMapper.mapToDto(hrNeedRequest);
    }

    public NeedRequestResponseDto updateNeedRequest(Long tenantId, Long id, NeedRequestRequestDto needRequestRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (needRequestRequest == null || needRequestRequest.getBudgetYearId() == null
                || needRequestRequest.getDepartmentId() == null
                || needRequestRequest.getStaffPlanId() == null) {
            throw new IllegalArgumentException("NeedRequestRequest and its fields must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrNeedRequest hrNeedRequest = needRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NeedRequest not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, needRequestRequest.getBudgetYearId());
        DepartmentDto department = tenantServiceClient.getDepartmentById(needRequestRequest.getDepartmentId(), tenantId);
        StaffPlanDto jobRegistration = tenantServiceClient.getStaffPlanById(needRequestRequest.getStaffPlanId(), tenantId);

        needRequestMapper.updateNeedRequest(hrNeedRequest, needRequestRequest);

        hrNeedRequest.setBudgetYearId(budgetYear.getId());
        hrNeedRequest.setDepartmentId(department.getId());
        hrNeedRequest.setStaffPlanId(jobRegistration.getId());

        HrNeedRequest updatedHrNeedRequest = needRequestRepository.save(hrNeedRequest);
        return needRequestMapper.mapToDto(updatedHrNeedRequest);
    }

    public void deleteNeedRequest(Long tenantId, Long id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        tenantServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrNeedRequest hrNeedRequest = needRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NeedRequest not found with id " + id));
        needRequestRepository.delete(hrNeedRequest);
    }
}
