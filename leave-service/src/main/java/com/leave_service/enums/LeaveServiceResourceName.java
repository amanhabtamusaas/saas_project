package com.leave_service.enums;

import lombok.Getter;

@Getter
public enum LeaveServiceResourceName {

    /* Leave Type */
    ADD_LEAVE_TYPE("Add Leave Type"),
    GET_ALL_LEAVE_TYPES("Get All Leave Types"),
    GET_LEAVE_TYPE_BY_ID("Get Leave Type Details"),
    UPDATE_LEAVE_TYPE("Update Leave Type"),
    DELETE_LEAVE_TYPE("Delete Leave Type"),

    /* Leave Setting */
    ADD_LEAVE_SETTING("Add Leave Setting"),
    GET_ALL_LEAVE_SETTINGS("Get All Leave Settings"),
    GET_LEAVE_SETTING_BY_ID("Get Leave Setting Details"),
    UPDATE_LEAVE_SETTING("Update Leave Setting"),
    DELETE_LEAVE_SETTING("Delete Leave Setting"),

    /* Leave Schedule */
    ADD_LEAVE_SCHEDULE("Add Leave Schedule"),
    GET_ALL_LEAVE_SCHEDULES("Get All Leave Schedules"),
    GET_LEAVE_SCHEDULE_BY_ID("Get Leave Schedule Details"),
    UPDATE_LEAVE_SCHEDULE("Update Leave Schedule"),
    DELETE_LEAVE_SCHEDULE("Delete Leave Schedule"),

    /* Leave Request */
    ADD_LEAVE_REQUEST("Add Leave Request"),
    GET_ALL_LEAVE_REQUESTS("Get All Leave Requests"),
    GET_LEAVE_REQUEST_BY_ID("Get Leave Request Details"),
    UPDATE_LEAVE_REQUEST("Update Leave Request"),
    DELETE_LEAVE_REQUEST("Delete Leave Request"),

    /* HR Leave Approve  */
    ADD_HR_APPROVE_LEAVE("Add HR Approve Leave"),
    GET_ALL_HR_APPROVED_LEAVES("Get All HR Approved Leaves"),
    GET_HR_APPROVED_LEAVE_BY_ID("Get HR Approved Leave  Details"),
    UPDATE_HR_APPROVED_LEAVE("Update HR Approved Leave "),
    DELETE_HR_APPROVED_LEAVE("Delete HR Approved Leave "),

    /* Department Leave Approve  */
    ADD_DEPARTMENT_APPROVE_LEAVE("Add Department Approve Leave"),
    GET_ALL_DEPARTMENT_APPROVED_LEAVES("Get All Department Approved Leaves"),
    GET_DEPARTMENT_APPROVED_LEAVE_BY_ID("Get Department Approved Leave  Details"),
    UPDATE_DEPARTMENT_APPROVED_LEAVE("Update Department Approved Leave "),
    DELETE_DEPARTMENT_APPROVED_LEAVE("Delete Department Approved Leave "),

    /* Holiday */
    ADD_HOLIDAY("Add Holiday"),
    GET_ALL_HOLIDAYS("Get All Holidays"),
    GET_HOLIDAY_BY_ID("Get Holiday Details"),
    UPDATE_HOLIDAY("Update Holiday"),
    DELETE_HOLIDAY("Delete Holiday"),

    /* Holiday Management */
    ADD_HOLIDAY_MANAGEMENT("Add Holiday Management"),
    GET_ALL_HOLIDAY_MANAGEMENTS("Get All Holiday Managements"),
    GET_HOLIDAY_MANAGEMENT_BY_ID("Get Holiday Management Details"),
    UPDATE_HOLIDAY_MANAGEMENT("Update Holiday Management"),
    DELETE_HOLIDAY_MANAGEMENT("Delete Holiday Management"),

    /* Budget Year */
    ADD_BUDGET_YEAR("Add Budget Year"),
    GET_ALL_BUDGET_YEARS("Get All Budget Years"),
    GET_BUDGET_YEAR_BY_ID("Get Budget Year Details"),
    UPDATE_BUDGET_YEAR("Update Budget Year"),
    DELETE_BUDGET_YEAR("Delete Budget Year"),

    /* Employee Leave Balance */
    GET_EMPLOYEE_LEAVE_BALANCE("Get Employee Leave Balance"),
    GET_ALL_EMPLOYEE_LEAVE_BALANCE_HISTORIES("Get All Employee Leave Balance Histories");

    private final String value;

    LeaveServiceResourceName(String value) {
        this.value = value;
    }
}
