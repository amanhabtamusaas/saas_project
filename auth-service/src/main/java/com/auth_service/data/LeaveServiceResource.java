package com.auth_service.data;

import com.auth_service.dto.clientDto.TenantDto;
import com.auth_service.enums.LeaveServiceResourceName;
import com.auth_service.exception.ResourceExistsException;
import com.auth_service.model.Resource;
import com.auth_service.repository.ResourceRepository;
import com.auth_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class LeaveServiceResource {

    private final ResourceRepository resourceRepository;
    private final ValidationUtil validationUtil;

    public void storeEndpoints(TenantDto tenant) {

        List<Resource> resources = new ArrayList<>();

        /* Leave Type */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_TYPE.getValue(), "Leave Type",
                null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_TYPES.getValue(), "Leave Type",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_TYPE_BY_ID.getValue(), "Leave Type",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_TYPE.getValue(), "Leave Type",
                null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_TYPE.getValue(), "Leave Type",
                null, tenant.getId()));

        /* Leave Setting */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_SETTING.getValue(), "Leave Setting",
                null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_SETTINGS.getValue(), "Leave Setting",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_SETTING_BY_ID.getValue(), "Leave Setting",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_SETTING.getValue(), "Leave Setting",
                null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_SETTING.getValue(), "Leave Setting",
                null, tenant.getId()));

        /* Leave Schedule */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_SCHEDULE.getValue(), "Leave Schedule",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_SCHEDULES.getValue(), "Leave Schedule",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_SCHEDULE_BY_ID.getValue(), "Leave Schedule",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_SCHEDULE.getValue(), "Leave Schedule",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_SCHEDULE.getValue(), "Leave Schedule",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* Leave Request */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_LEAVE_REQUEST.getValue(), "Leave Request",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_LEAVE_REQUESTS.getValue(), "Leave Request",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_LEAVE_REQUEST_BY_ID.getValue(), "Leave Request",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_LEAVE_REQUEST.getValue(), "Leave Request",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_LEAVE_REQUEST.getValue(), "Leave Request",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        /* HR Leave Approve */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_HR_APPROVE_LEAVE.getValue(),
                "HR Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_HR_APPROVED_LEAVES.getValue(),
                "HR Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_HR_APPROVED_LEAVE_BY_ID.getValue(),
                "HR Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_HR_APPROVED_LEAVE.getValue(),
                "HR Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_HR_APPROVED_LEAVE.getValue(),
                "HR Leave Approve", null, tenant.getId()));

        /* Department Leave Approve */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_DEPARTMENT_APPROVE_LEAVE.getValue(),
                "Department Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_DEPARTMENT_APPROVED_LEAVES.getValue(),
                "Department Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_DEPARTMENT_APPROVED_LEAVE_BY_ID.getValue(),
                "Department Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_DEPARTMENT_APPROVED_LEAVE.getValue(),
                "Department Leave Approve", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_DEPARTMENT_APPROVED_LEAVE.getValue(),
                "Department Leave Approve", null, tenant.getId()));

        /* Holiday */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_HOLIDAY.getValue(),
                "Holiday", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_HOLIDAYS.getValue(),
                "Holiday", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_HOLIDAY_BY_ID.getValue(),
                "Holiday", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_HOLIDAY.getValue(),
                "Holiday", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_HOLIDAY.getValue(),
                "Holiday", null, tenant.getId()));

        /* Holiday Management */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_HOLIDAY_MANAGEMENT.getValue(),
                "Holiday Management", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_HOLIDAY_MANAGEMENTS.getValue(),
                "Holiday Management", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_HOLIDAY_MANAGEMENT_BY_ID.getValue(),
                "Holiday Management", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_HOLIDAY_MANAGEMENT.getValue(),
                "Holiday Management", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_HOLIDAY_MANAGEMENT.getValue(),
                "Holiday Management", null, tenant.getId()));

        /* Budget Year */
        resources.add(mapToEntity(LeaveServiceResourceName.ADD_BUDGET_YEAR.getValue(),
                "Budget Year", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_BUDGET_YEARS.getValue(), "Budget Year",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_BUDGET_YEAR_BY_ID.getValue(), "Budget Year",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.UPDATE_BUDGET_YEAR.getValue(),
                "Budget Year", null, tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.DELETE_BUDGET_YEAR.getValue(),
                "Budget Year", null, tenant.getId()));

        /* Employee Leave Balance */
        resources.add(mapToEntity(LeaveServiceResourceName.GET_EMPLOYEE_LEAVE_BALANCE.getValue(), "Employee Leave Balance",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));
        resources.add(mapToEntity(LeaveServiceResourceName.GET_ALL_EMPLOYEE_LEAVE_BALANCE_HISTORIES.getValue(), "Employee Leave Balance",
                tenant.getAbbreviatedName().toLowerCase() + "_default_role", tenant.getId()));

        for (Resource resource : resources) {
            if (resourceRepository.existsByTenantIdAndResourceName(tenant.getId(), resource.getResourceName())) {
                throw new ResourceExistsException(
                        "Resource already exists with name '" + resource.getResourceName() + "'");
            }
            resourceRepository.save(resource);
        }
    }

    private Resource mapToEntity(String requestName,
                                 String groupName,
                                 String roleName,
                                 UUID tenantId) {

        Resource resource = new Resource();
        resource.setResourceName(requestName);
        resource.setGroupName(groupName);
        resource.setTenantId(tenantId);
        resource.setDescription("");

        Set<String> roles = new HashSet<>();
        TenantDto tenant = validationUtil.getTenantById(tenantId);
        String adminRole = tenant.getAbbreviatedName().toLowerCase() + "_admin";
        roles.add(adminRole);
        if (roleName != null) {
            roles.add(roleName);
        }
        resource.setRequiredRoles(roles);

        return resource;
    }
}
