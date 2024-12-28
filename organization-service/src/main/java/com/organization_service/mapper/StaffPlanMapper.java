package com.organization_service.mapper;

import com.organization_service.dto.requestDto.StaffPlanRequest;
import com.organization_service.dto.responseDto.StaffPlanResponse;
import com.organization_service.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffPlanMapper {

    @Autowired
    private TenantMapper tenantMapper;

    public StaffPlan mapToEntity(StaffPlanRequest staffPlanRequest) {
        StaffPlan staffPlan = new StaffPlan();

        staffPlan.setQuantity(staffPlanRequest.getQuantity());

        if (staffPlanRequest.getJobRegistrationId() != null) {
            JobRegistration jobCode = new JobRegistration();
            jobCode.setId(staffPlanRequest.getJobRegistrationId());
            staffPlan.setJobRegistration(jobCode);
        }
        if (staffPlanRequest.getDepartmentId() != null) {
            Department department = new Department();
            department.setId(staffPlanRequest.getDepartmentId());  // Corrected method call
            staffPlan.setDepartment(department);
        }


        return staffPlan;
    }

    public StaffPlanResponse mapToDto(StaffPlan staffPlan) {
        StaffPlanResponse response = new StaffPlanResponse();

        response.setId(staffPlan.getId());
        response.setQuantity(staffPlan.getQuantity());
        response.setCreatedAt(staffPlan.getCreatedAt());
        response.setUpdatedAt(staffPlan.getUpdatedAt());
        response.setCreatedBy(staffPlan.getCreatedBy());
        response.setUpdatedBy(staffPlan.getUpdatedBy());

        if (staffPlan.getTenant() != null) {
            response.setTenantId(staffPlan.getTenant().getId());
        }
        if (staffPlan.getJobRegistration() != null) {
            response.setJobRegistrationId(staffPlan.getJobRegistration().getId());
        }
        if (staffPlan.getDepartment() != null) {
            response.setDepartmentId(staffPlan.getDepartment().getId());
        }


        return response;
    }

    public StaffPlan updateStaffPlan(StaffPlan staffPlan, StaffPlanRequest staffPlanRequest) {
        if (staffPlanRequest.getQuantity() != null) {
            staffPlan.setQuantity(staffPlanRequest.getQuantity());
        }

//        if (staffPlanRequest.getJobRegistrationId() != null) {
//            JobRegistration jobCode = new JobRegistration();
//            jobCode.setId(staffPlanRequest.getJobRegistrationId());
//            staffPlan.setJobRegistration(jobCode);
//        }
//

        return staffPlan;
    }
}
