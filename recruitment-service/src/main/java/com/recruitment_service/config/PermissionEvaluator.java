package com.recruitment_service.config;

import com.recruitment_service.enums.RecruitmentServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final RoleConverter roleConverter;

    /* Shortlist Criteria Permissions */
    public void addShortlistCriteriaPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_SHORTLIST_CRITERIA);
    }

    public void getAllShortlistCriteriaPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_SHORTLIST_CRITERIA);
    }

    public void getShortlistCriteriaByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_SHORTLIST_CRITERIA_BY_ID);
    }

    public void updateShortlistCriteriaPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_SHORTLIST_CRITERIA);
    }

    public void deleteShortlistCriteriaPermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_SHORTLIST_CRITERIA);
    }

    /* Recruitment Permissions */
    public void addRecruitmentPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_RECRUITMENT);
    }

    public void getAllRecruitmentsPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_RECRUITMENTS);
    }

    public void getRecruitmentByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_RECRUITMENT_BY_ID);
    }

    public void getRecruitmentByVacancyNumberPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_RECRUITMENT_BY_VACANCY_NUMBER);
    }

    public void getRecruitmentsByStatusPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_RECRUITMENTS_BY_STATUS);
    }

    public void updateRecruitmentPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_RECRUITMENT);
    }

    public void approveRecruitmentPermission() {
        checkPermission(RecruitmentServiceResourceName.APPROVE_RECRUITMENT);
    }

    public void deleteRecruitmentPermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_RECRUITMENT);
    }

    /* Media Type Permissions */
    public void addMediaTypePermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_MEDIA_TYPE);
    }

    public void getAllMediaTypesPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_MEDIA_TYPES);
    }

    public void getMediaTypesByAdvertisementIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_MEDIA_TYPES_BY_ADVERTISEMENT_ID);
    }

    public void getMediaTypeByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_MEDIA_TYPE_BY_ID);
    }

    public void updateMediaTypePermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_MEDIA_TYPE);
    }

    public void deleteMediaTypePermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_MEDIA_TYPE);
    }

    /* Exam Result Permissions */
    public void addExamResultPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_EXAM_RESULT);
    }

    public void getAllExamResultsPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_EXAM_RESULTS);
    }

    public void getExamResultByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_EXAM_RESULT_BY_ID);
    }

    public void updateExamResultPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_EXAM_RESULT);
    }

    /* Assessment Weight Permissions */
    public void addAssessmentWeightPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_ASSESSMENT_WEIGHT);
    }

    public void getAllAssessmentWeightsPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_ASSESSMENT_WEIGHTS);
    }

    public void getAssessmentWeightByRecruitmentIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_RECRUITMENT_ID);
    }

    public void getAssessmentWeightByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ASSESSMENT_WEIGHT_BY_ID);
    }

    public void updateAssessmentWeightPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_ASSESSMENT_WEIGHT);
    }

    /* Applicant Permissions */
    public void addApplicantPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_APPLICANT);
    }

    public void getAllApplicantsPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_APPLICANTS);
    }

    public void getApplicantByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_APPLICANT_BY_ID);
    }

    public void updateApplicantPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_APPLICANT);
    }

    public void deleteApplicantPermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_APPLICANT);
    }

    /* Applicant Training or Certificate Permissions */
    public void addTrainingPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_TRAINING);
    }

    public void getAllTrainingsPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_TRAININGS);
    }

    public void getTrainingByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_TRAINING_BY_ID);
    }

    public void downloadTrainingCertificatePermission() {
        checkPermission(RecruitmentServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE);
    }

    public void updateTrainingPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_TRAINING);
    }

    public void deleteTrainingPermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_TRAINING);
    }

    /* Applicant Reference Permissions */
    public void addReferencePermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_REFERENCE);
    }

    public void getAllReferencesPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_REFERENCES);
    }

    public void getReferenceByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_REFERENCE_BY_ID);
    }

    public void updateReferencePermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_REFERENCE);
    }

    public void deleteReferencePermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_REFERENCE);
    }

    /* Applicant Language Permissions */
    public void addLanguagePermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_LANGUAGE);
    }

    public void getAllLanguagesPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_LANGUAGES);
    }

    public void getLanguageByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_LANGUAGE_BY_ID);
    }

    public void updateLanguagePermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_LANGUAGE);
    }

    public void deleteLanguagePermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_LANGUAGE);
    }

    /* Applicant Experience Permissions */
    public void addExperiencePermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_EXPERIENCE);
    }

    public void getAllExperiencesPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_EXPERIENCES);
    }

    public void getExperienceByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_EXPERIENCE_BY_ID);
    }

    public void downloadExperienceDocumentPermission() {
        checkPermission(RecruitmentServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT);
    }

    public void updateExperiencePermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_EXPERIENCE);
    }

    public void deleteExperiencePermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_EXPERIENCE);
    }

    /* Applicant Education Permissions */
    public void addEducationPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_EDUCATION);
    }

    public void getAllEducationsPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_EDUCATIONS);
    }

    public void getEducationByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_EDUCATION_BY_ID);
    }

    public void downloadEducationDocumentPermission() {
        checkPermission(RecruitmentServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT);
    }

    public void updateEducationPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_EDUCATION);
    }

    public void deleteEducationPermission() {
        checkPermission(RecruitmentServiceResourceName.DELETE_EDUCATION);
    }

    /* Advertisement Permissions */
    public void addAdvertisementPermission() {
        checkPermission(RecruitmentServiceResourceName.ADD_ADVERTISEMENT);
    }

    public void getAllAdvertisementsPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ALL_ADVERTISEMENTS);
    }

    public void getAdvertisementByIdPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_ID);
    }

    public void getAdvertisementByVacancyNumberPermission() {
        checkPermission(RecruitmentServiceResourceName.GET_ADVERTISEMENT_BY_VACANCY_NUMBER);
    }

    public void updateAdvertisementPermission() {
        checkPermission(RecruitmentServiceResourceName.UPDATE_ADVERTISEMENT);
    }

    public void removeAdvertisementMediaTypePermission() {
        checkPermission(RecruitmentServiceResourceName.REMOVE_ADVERTISEMENT_MEDIA_TYPE);
    }

    private void checkPermission(RecruitmentServiceResourceName resourceName) {
        boolean hasPermission = roleConverter.hasPermission(resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}