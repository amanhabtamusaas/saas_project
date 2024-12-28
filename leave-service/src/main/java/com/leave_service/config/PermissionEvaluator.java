package com.leave_service.config;

import com.leave_service.enums.LeaveServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final RoleConverter roleConverter;

    /* Leave Type Permissions */
    public void addLeaveTypePermission() {
        checkPermission(LeaveServiceResourceName.ADD_LEAVE_TYPE);
    }

    public void getAllLeaveTypesPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_LEAVE_TYPES);
    }

    public void getLeaveTypeByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_LEAVE_TYPE_BY_ID);
    }

    public void updateLeaveTypePermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_LEAVE_TYPE);
    }

    public void deleteLeaveTypePermission() {
        checkPermission(LeaveServiceResourceName.DELETE_LEAVE_TYPE);
    }

    /* Leave Setting Permissions */
    public void addLeaveSettingPermission() {
        checkPermission(LeaveServiceResourceName.ADD_LEAVE_SETTING);
    }

    public void getAllLeaveSettingsPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_LEAVE_SETTINGS);
    }

    public void getLeaveSettingByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_LEAVE_SETTING_BY_ID);
    }

    public void updateLeaveSettingPermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_LEAVE_SETTING);
    }

    public void deleteLeaveSettingPermission() {
        checkPermission(LeaveServiceResourceName.DELETE_LEAVE_SETTING);
    }

    /* Leave Schedule Permissions */
    public void addLeaveSchedulePermission() {
        checkPermission(LeaveServiceResourceName.ADD_LEAVE_SCHEDULE);
    }

    public void getAllLeaveSchedulesPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_LEAVE_SCHEDULES);
    }

    public void getLeaveScheduleByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_LEAVE_SCHEDULE_BY_ID);
    }

    public void updateLeaveSchedulePermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_LEAVE_SCHEDULE);
    }

    public void deleteLeaveSchedulePermission() {
        checkPermission(LeaveServiceResourceName.DELETE_LEAVE_SCHEDULE);
    }

    /* Leave Request Permissions */
    public void addLeaveRequestPermission() {
        checkPermission(LeaveServiceResourceName.ADD_LEAVE_REQUEST);
    }

    public void getAllLeaveRequestsPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_LEAVE_REQUESTS);
    }

    public void getLeaveRequestByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_LEAVE_REQUEST_BY_ID);
    }

    public void updateLeaveRequestPermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_LEAVE_REQUEST);
    }

    public void deleteLeaveRequestPermission() {
        checkPermission(LeaveServiceResourceName.DELETE_LEAVE_REQUEST);
    }

    /* HR Leave Approve Permissions */
    public void addHrApproveLeavePermission() {
        checkPermission(LeaveServiceResourceName.ADD_HR_APPROVE_LEAVE);
    }

    public void getAllHrApprovedLeavesPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_HR_APPROVED_LEAVES);
    }

    public void getHrApprovedLeaveByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_HR_APPROVED_LEAVE_BY_ID);
    }

    public void updateHrApprovedLeavePermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_HR_APPROVED_LEAVE);
    }

    public void deleteHrApprovedLeavePermission() {
        checkPermission(LeaveServiceResourceName.DELETE_HR_APPROVED_LEAVE);
    }

    /* Department Leave Approve Permissions */
    public void addDepartmentApproveLeavePermission() {
        checkPermission(LeaveServiceResourceName.ADD_DEPARTMENT_APPROVE_LEAVE);
    }

    public void getAllDepartmentApprovedLeavesPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_DEPARTMENT_APPROVED_LEAVES);
    }

    public void getDepartmentApprovedLeaveByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_DEPARTMENT_APPROVED_LEAVE_BY_ID);
    }

    public void updateDepartmentApprovedLeavePermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_DEPARTMENT_APPROVED_LEAVE);
    }

    public void deleteDepartmentApprovedLeavePermission() {
        checkPermission(LeaveServiceResourceName.DELETE_DEPARTMENT_APPROVED_LEAVE);
    }

    /* Holiday Permissions */
    public void addHolidayPermission() {
        checkPermission(LeaveServiceResourceName.ADD_HOLIDAY);
    }

    public void getAllHolidaysPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_HOLIDAYS);
    }

    public void getHolidayByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_HOLIDAY_BY_ID);
    }

    public void updateHolidayPermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_HOLIDAY);
    }

    public void deleteHolidayPermission() {
        checkPermission(LeaveServiceResourceName.DELETE_HOLIDAY);
    }

    /* Holiday Management Permissions */
    public void addHolidayManagementPermission() {
        checkPermission(LeaveServiceResourceName.ADD_HOLIDAY_MANAGEMENT);
    }

    public void getAllHolidayManagementsPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_HOLIDAY_MANAGEMENTS);
    }

    public void getHolidayManagementByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_HOLIDAY_MANAGEMENT_BY_ID);
    }

    public void updateHolidayManagementPermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_HOLIDAY_MANAGEMENT);
    }

    public void deleteHolidayManagementPermission() {
        checkPermission(LeaveServiceResourceName.DELETE_HOLIDAY_MANAGEMENT);
    }

    /* Budget Year Permissions */
    public void addBudgetYearPermission() {
        checkPermission(LeaveServiceResourceName.ADD_BUDGET_YEAR);
    }

    public void getAllBudgetYearsPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_BUDGET_YEARS);
    }

    public void getBudgetYearByIdPermission() {
        checkPermission(LeaveServiceResourceName.GET_BUDGET_YEAR_BY_ID);
    }

    public void updateBudgetYearPermission() {
        checkPermission(LeaveServiceResourceName.UPDATE_BUDGET_YEAR);
    }

    public void deleteBudgetYearPermission() {
        checkPermission(LeaveServiceResourceName.DELETE_BUDGET_YEAR);
    }

    /* Employee Leave Balance Permissions */
    public void getEmployeeLeaveBalancePermission() {
        checkPermission(LeaveServiceResourceName.GET_EMPLOYEE_LEAVE_BALANCE);
    }

    public void getAllEmployeeLeaveBalanceHistoriesPermission() {
        checkPermission(LeaveServiceResourceName.GET_ALL_EMPLOYEE_LEAVE_BALANCE_HISTORIES);
    }

    private void checkPermission(LeaveServiceResourceName resourceName) {
        boolean hasPermission = roleConverter.hasPermission(resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}