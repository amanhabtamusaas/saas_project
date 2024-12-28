package com.organization_service.config;

import com.organization_service.enums.OrganizationServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final RoleConverter roleConverter;

    /* Tenant Permissions */
    public void getTenantByIdPermission() {
        boolean isAdmin = roleConverter.isAdmin();
        if (!isAdmin){
            checkPermission(OrganizationServiceResourceName.GET_TENANT_BY_ID);
        }
    }

    public void downloadTenantLogoPermission() {
        boolean isAdmin = roleConverter.isAdmin();
        if (!isAdmin){
            checkPermission(OrganizationServiceResourceName.DOWNLOAD_TENANT_LOGO);
        }
    }

    public void updateTenantPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_TENANT);
    }

    /* Work Unit Permissions */
    public void addWorkUnitPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_WORK_UNIT);
    }

    public void getAllWorkUnitsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_WORK_UNITS);
    }

    public void getWorkUnitByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_WORK_UNIT_BY_ID);
    }

    public void updateWorkUnitPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_WORK_UNIT);
    }

    public void deleteWorkUnitPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_WORK_UNIT);
    }

    /* Staff Plan Permissions */
    public void addStaffPlanPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_STAFF_PLAN);
    }

    public void getAllStaffPlansPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_STAFF_PLANS);
    }

    public void getStaffPlansByDepartmentIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_STAFF_PLANS_BY_DEPARTMENT_ID);
    }

    public void updateStaffPlanPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_STAFF_PLAN);
    }

    public void deleteStaffPlanPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_STAFF_PLAN);
    }

    /* Qualification Permissions */
    public void addQualificationPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_QUALIFICATION);
    }

    public void getAllQualificationsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_QUALIFICATIONS);
    }

    public void getQualificationByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_QUALIFICATION_BY_ID);
    }

    public void updateQualificationPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_QUALIFICATION);
    }

    public void deleteQualificationPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_QUALIFICATION);
    }

    /* Pay Grade Permissions */
    public void addPayGradePermission() {
        checkPermission(OrganizationServiceResourceName.ADD_PAY_GRADE);
    }

    public void getAllPayGradesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_PAY_GRADES);
    }

    public void getPayGradeByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_PAY_GRADE_BY_ID);
    }

    public void getPayGradesByJobGradeIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_PAY_GRADES_BY_JOB_GRADE_ID);
    }

    public void updatePayGradePermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_PAY_GRADE);
    }

    public void deletePayGradePermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_PAY_GRADE);
    }

    /* Location Permissions */
    public void addLocationPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_LOCATION);
    }

    public void getAllLocationsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_LOCATIONS);
    }

    public void getLocationByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_LOCATION_BY_ID);
    }

    public void addSubLocationPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_SUB_LOCATION);
    }

    public void getAllParentLocationsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_PARENT_LOCATIONS);
    }

    public void getAllSubLocationsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_SUB_LOCATIONS);
    }

    public void updateLocationPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_LOCATION);
    }

    public void deleteSubLocationPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_SUB_LOCATION);
    }

    public void deleteLocationPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_LOCATION);
    }

    /* Job Permissions */
    public void addJobPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_JOB);
    }

    public void getAllJobsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_JOBS);
    }

    public void getJobByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_JOB_BY_ID);
    }

    public void getJobsByDepartmentIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_JOBS_BY_DEPARTMENT_ID);
    }

    public void updateJobPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_JOB);
    }

    public void deleteJobPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_JOB);
    }

    /* Job Grade Permissions */
    public void addJobGradePermission() {
        checkPermission(OrganizationServiceResourceName.ADD_JOB_GRADE);
    }

    public void getAllJobGradesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_JOB_GRADES);
    }

    public void getJobGradeByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_JOB_GRADE_BY_ID);
    }

    public void updateJobGradePermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_JOB_GRADE);
    }

    public void deleteJobGradePermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_JOB_GRADE);
    }

    /* Job Category Permissions */
    public void addJobCategoryPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_JOB_CATEGORY);
    }

    public void getAllJobCategoriesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_JOB_CATEGORIES);
    }

    public void getJobCategoryByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_JOB_CATEGORY_BY_ID);
    }

    public void updateJobCategoryPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_JOB_CATEGORY);
    }

    public void deleteJobCategoryPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_JOB_CATEGORY);
    }

    /* Field of Study Permissions */
    public void addFieldOfStudyPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_FIELD_OF_STUDY);
    }

    public void getAllFieldOfStudiesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_FIELD_OF_STUDIES);
    }

    public void getFieldOfStudyByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_FIELD_OF_STUDY_BY_ID);
    }

    public void updateFieldOfStudyPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_FIELD_OF_STUDY);
    }

    public void deleteFieldOfStudyPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_FIELD_OF_STUDY);
    }

    /* Education Level Permissions */
    public void addEducationLevelPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_EDUCATION_LEVEL);
    }

    public void getAllEducationLevelsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_EDUCATION_LEVELS);
    }

    public void getEducationLevelByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_EDUCATION_LEVEL_BY_ID);
    }

    public void updateEducationLevelPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_EDUCATION_LEVEL);
    }

    public void deleteEducationLevelPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_EDUCATION_LEVEL);
    }

    /* Department Permissions */
    public void addDepartmentPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_DEPARTMENT);
    }

    public void getAllDepartmentsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_DEPARTMENTS);
    }

    public void getDepartmentByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_DEPARTMENT_BY_ID);
    }

    public void addSubDepartmentPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_SUB_DEPARTMENT);
    }

    public void transferParentDepartmentPermission() {
        checkPermission(OrganizationServiceResourceName.TRANSFER_PARENT_DEPARTMENT);
    }

    public void transferSubDepartmentPermission() {
        checkPermission(OrganizationServiceResourceName.TRANSFER_SUB_DEPARTMENT);
    }

    public void getAllParentDepartmentsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_PARENT_DEPARTMENTS);
    }

    public void getAllSubDepartmentsPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_SUB_DEPARTMENTS);
    }

    public void updateDepartmentPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_DEPARTMENT);
    }

    public void deleteSubDepartmentPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_SUB_DEPARTMENT);
    }

    public void deleteDepartmentPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_DEPARTMENT);
    }

    public void getAllDepartmentHistoriesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_DEPARTMENT_HISTORIES);
    }

    /* Department Type Permissions */
    public void addDepartmentTypePermission() {
        checkPermission(OrganizationServiceResourceName.ADD_DEPARTMENT_TYPE);
    }

    public void getAllDepartmentTypesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_DEPARTMENT_TYPES);
    }

    public void getDepartmentTypeByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_DEPARTMENT_TYPE_BY_ID);
    }

    public void updateDepartmentTypePermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_DEPARTMENT_TYPE);
    }

    public void deleteDepartmentTypePermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_DEPARTMENT_TYPE);
    }

    /* Address Permissions */
    public void addAddressPermission() {
        checkPermission(OrganizationServiceResourceName.ADD_ADDRESS);
    }

    public void getAllAddressesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_ADDRESSES);
    }

    public void getAddressesByDepartmentIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ADDRESSES_BY_DEPARTMENT_ID);
    }

    public void updateAddressPermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_ADDRESS);
    }

    public void deleteAddressPermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_ADDRESS);
    }

    /* Location Type Permissions */
    public void addLocationTypePermission() {
        checkPermission(OrganizationServiceResourceName.ADD_LOCATION_TYPE);
    }

    public void getAllLocationTypesPermission() {
        checkPermission(OrganizationServiceResourceName.GET_ALL_LOCATION_TYPES);
    }

    public void getLocationTypeByIdPermission() {
        checkPermission(OrganizationServiceResourceName.GET_LOCATION_TYPE_BY_ID);
    }

    public void updateLocationTypePermission() {
        checkPermission(OrganizationServiceResourceName.UPDATE_LOCATION_TYPE);
    }

    public void deleteLocationTypePermission() {
        checkPermission(OrganizationServiceResourceName.DELETE_LOCATION_TYPE);
    }

    private void checkPermission(OrganizationServiceResourceName resourceName) {
        boolean hasPermission = roleConverter.hasPermission(resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}