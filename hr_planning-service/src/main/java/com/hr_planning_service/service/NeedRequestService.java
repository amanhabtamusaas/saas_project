package com.hr_planning_service.service;


import com.hr_planning_service.client.LeaveServiceClient;
import com.hr_planning_service.client.OrganizationServiceClient;
import com.hr_planning_service.dto.*;
import com.hr_planning_service.dto.request.NeedRequestRequestDto;
import com.hr_planning_service.dto.response.NeedRequestResponseDto;
import com.hr_planning_service.model.HrNeedRequest;
import com.hr_planning_service.exception.ResourceNotFoundException;
import com.hr_planning_service.mapper.NeedRequestMapper;
import com.hr_planning_service.repository.NeedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NeedRequestService {

    private final NeedRequestRepository needRequestRepository;
    private final NeedRequestMapper needRequestMapper;
    private final OrganizationServiceClient organizationServiceClient;
    private final LeaveServiceClient leaveServiceClient;

    public NeedRequestResponseDto createNeedRequest(UUID tenantId, NeedRequestRequestDto needRequestRequest) {

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, needRequestRequest.getBudgetYearId());
        DepartmentDto department = organizationServiceClient.getDepartmentById(needRequestRequest.getDepartmentId(), tenantId);
        StaffPlanDto jobRegistration = organizationServiceClient.getStaffPlanById(needRequestRequest.getStaffPlanId(), tenantId);
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (needRequestRequest == null || needRequestRequest.getBudgetYearId() == null
                || needRequestRequest.getDepartmentId() == null
                || needRequestRequest.getStaffPlanId() == null) {
            throw new IllegalArgumentException("NeedRequestRequest and its fields must not be null");
        }



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

    public List<NeedRequestResponseDto> getAllNeedRequests(UUID tenantId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        List<HrNeedRequest> hrNeedRequests = needRequestRepository.findAll();
        return hrNeedRequests.stream()
                .map(needRequestMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public NeedRequestResponseDto getNeedRequestById(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrNeedRequest hrNeedRequest = needRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NeedRequest not found with id " + id));
        return needRequestMapper.mapToDto(hrNeedRequest);
    }
    public List<NeedRequestResponseDto> getNeedRequestByStaffPlanId(UUID tenantId, UUID staffPlanId) {
        // Fetch tenant data, throwing an exception if not found
        TenantDto tenant = organizationServiceClient.getTenantById(tenantId);
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        // Fetch staff plan data, throwing an exception if not found
        StaffPlanDto staffPlan = organizationServiceClient.getStaffPlanById(tenantId,staffPlanId);
        if (staffPlanId == null) {
            throw new IllegalArgumentException("StaffPlanId must not be null");
        }


        // Retrieve the need requests associated with the given staff plan
        List<HrNeedRequest> hrNeedRequestList = needRequestRepository.findByStaffPlanId(tenantId,staffPlanId);

        // Map the HrNeedRequest list to NeedRequestResponseDto while ensuring both tenant and staff plan match
        return hrNeedRequestList.stream()
                .map(needRequestMapper::mapToDto) // Map each HrNeedRequest to NeedRequestResponseDto
                .collect(Collectors.toList());
    }


    public NeedRequestResponseDto updateNeedRequest(UUID tenantId, UUID id, NeedRequestRequestDto needRequestRequest) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        if (needRequestRequest == null || needRequestRequest.getBudgetYearId() == null
                || needRequestRequest.getDepartmentId() == null
                || needRequestRequest.getStaffPlanId() == null) {
            throw new IllegalArgumentException("NeedRequestRequest and its fields must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrNeedRequest hrNeedRequest = needRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NeedRequest not found with id " + id));

        BudgetYearDto budgetYear = leaveServiceClient.getBudgetYearById(tenantId, needRequestRequest.getBudgetYearId());
        DepartmentDto department = organizationServiceClient.getDepartmentById(needRequestRequest.getDepartmentId(), tenantId);
        StaffPlanDto jobRegistration = organizationServiceClient.getStaffPlanById(needRequestRequest.getStaffPlanId(), tenantId);

        needRequestMapper.updateNeedRequest(hrNeedRequest, needRequestRequest);

        hrNeedRequest.setBudgetYearId(budgetYear.getId());
        hrNeedRequest.setDepartmentId(department.getId());
        hrNeedRequest.setStaffPlanId(jobRegistration.getId());

        HrNeedRequest updatedHrNeedRequest = needRequestRepository.save(hrNeedRequest);
        return needRequestMapper.mapToDto(updatedHrNeedRequest);
    }

    public void deleteNeedRequest(UUID tenantId, UUID id) {
        if (tenantId == null) {
            throw new IllegalArgumentException("TenantId must not be null");
        }

        organizationServiceClient.getTenantById(tenantId); // Ensure tenant exists

        HrNeedRequest hrNeedRequest = needRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("NeedRequest not found with id " + id));
        needRequestRepository.delete(hrNeedRequest);
    }
}
