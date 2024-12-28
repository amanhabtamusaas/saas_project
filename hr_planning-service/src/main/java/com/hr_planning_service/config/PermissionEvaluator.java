package com.hr_planning_service.config;

import com.hr_planning_service.enums.HrPlanningServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final RoleConverter roleConverter;

    /* Annual Recruitment and Promotion Permissions */
    public void addAnnualRecruitmentAndPromotionPermission() {
        checkPermission(HrPlanningServiceResourceName.ADD_ANNUAL_RECRUITMENT_AND_PROMOTION);
    }

    public void getAllAnnualRecruitmentAndPromotionsPermission() {
        checkPermission(HrPlanningServiceResourceName.GET_ALL_ANNUAL_RECRUITMENT_AND_PROMOTIONS);
    }

    public void getAnnualRecruitmentAndPromotionByIdPermission() {
        checkPermission(HrPlanningServiceResourceName.GET_ANNUAL_RECRUITMENT_AND_PROMOTION_BY_ID);
    }

    public void updateAnnualRecruitmentAndPromotionPermission() {
        checkPermission(HrPlanningServiceResourceName.UPDATE_ANNUAL_RECRUITMENT_AND_PROMOTION);
    }

    public void deleteAnnualRecruitmentAndPromotionPermission() {
        checkPermission(HrPlanningServiceResourceName.DELETE_ANNUAL_RECRUITMENT_AND_PROMOTION);
    }

    /* HR Need Request Permissions */
    public void addHrNeedRequestPermission() {
        checkPermission(HrPlanningServiceResourceName.ADD_HR_NEED_REQUEST);
    }

    public void getAllHrNeedRequestsPermission() {
        checkPermission(HrPlanningServiceResourceName.GET_ALL_HR_NEED_REQUESTS);
    }

    public void getHrNeedRequestByIdPermission() {
        checkPermission(HrPlanningServiceResourceName.GET_HR_NEED_REQUEST_BY_ID);
    }

    public void updateHrNeedRequestPermission() {
        checkPermission(HrPlanningServiceResourceName.UPDATE_HR_NEED_REQUEST);
    }

    public void deleteHrNeedRequestPermission() {
        checkPermission(HrPlanningServiceResourceName.DELETE_HR_NEED_REQUEST);
    }

    /* HR Analysis Permissions */
    public void addHrAnalysisPermission() {
        checkPermission(HrPlanningServiceResourceName.ADD_HR_ANALYSIS);
    }

    public void getAllHrAnalysesPermission() {
        checkPermission(HrPlanningServiceResourceName.GET_ALL_HR_ANALYSES);
    }

    public void getHrAnalysisByIdPermission() {
        checkPermission(HrPlanningServiceResourceName.GET_HR_ANALYSIS_BY_ID);
    }

    public void updateHrAnalysisPermission() {
        checkPermission(HrPlanningServiceResourceName.UPDATE_HR_ANALYSIS);
    }

    public void deleteHrAnalysisPermission() {
        checkPermission(HrPlanningServiceResourceName.DELETE_HR_ANALYSIS);
    }

    private void checkPermission(HrPlanningServiceResourceName resourceName) {
        boolean hasPermission = roleConverter.hasPermission(resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}