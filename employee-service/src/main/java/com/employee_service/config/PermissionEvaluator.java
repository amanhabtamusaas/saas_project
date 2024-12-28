package com.employee_service.config;

import com.employee_service.enums.EmployeeServiceResourceName;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionEvaluator {

    private final RoleConverter roleConverter;

    /* Employee Permissions */
    public void addEmployeePermission() {
        checkPermission(EmployeeServiceResourceName.ADD_EMPLOYEE);
    }

    public void getAllEmployeesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_EMPLOYEES);
    }

    public void getEmployeeByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_EMPLOYEE_BY_ID);
    }

    public void getEmployeeByNamePermission() {
        checkPermission(EmployeeServiceResourceName.GET_EMPLOYEE_BY_NAME);
    }

    public void getEmployeeByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_EMPLOYEE_BY_EMPLOYEE_ID);
    }

    public void downloadEmployeeImagePermission() {
        checkPermission(EmployeeServiceResourceName.DOWNLOAD_EMPLOYEE_IMAGE);
    }

    public void getEmployeesByDepartmentIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_EMPLOYEES_BY_DEPARTMENT_ID);
    }

    public void updateEmployeePermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_EMPLOYEE);
    }

    public void deleteEmployeePermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_EMPLOYEE);
    }

    /* Employee Training or Certificate Permissions */
    public void addTrainingPermission() {
        checkPermission(EmployeeServiceResourceName.ADD_TRAINING);
    }

    public void getAllTrainingsPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_TRAININGS);
    }

    public void getTrainingByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_TRAINING_BY_ID);
    }

    public void getTrainingsByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_TRAININGS_BY_EMPLOYEE_ID);
    }

    public void downloadTrainingCertificatePermission() {
        checkPermission(EmployeeServiceResourceName.DOWNLOAD_TRAINING_CERTIFICATE);
    }

    public void updateTrainingPermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_TRAINING);
    }

    public void deleteTrainingPermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_TRAINING);
    }

    /* Title Name Permissions */
    public void addTitleNamePermission() {
        checkPermission(EmployeeServiceResourceName.ADD_TITLE_NAME);
    }

    public void getAllTitleNamesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_TITLE_NAMES);
    }

    public void getTitleNameByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_TITLE_NAME_BY_ID);
    }

    public void updateTitleNamePermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_TITLE_NAME);
    }

    public void deleteTitleNamePermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_TITLE_NAME);
    }

    /* Employee Skill Permissions */
    public void addSkillPermission() {
        checkPermission(EmployeeServiceResourceName.ADD_SKILL);
    }

    public void getAllSkillsPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_SKILLS);
    }

    public void getSkillByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_SKILL_BY_ID);
    }

    public void getSkillsByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_SKILLS_BY_EMPLOYEE_ID);
    }

    public void updateSkillPermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_SKILL);
    }

    public void deleteSkillPermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_SKILL);
    }

    /* Employee Reference Permissions */
    public void addReferencePermission() {
        checkPermission(EmployeeServiceResourceName.ADD_REFERENCE);
    }

    public void getAllReferencesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_REFERENCES);
    }

    public void getReferenceByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_REFERENCE_BY_ID);
    }

    public void getReferencesByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_REFERENCES_BY_EMPLOYEE_ID);
    }

    public void updateReferencePermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_REFERENCE);
    }

    public void deleteReferencePermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_REFERENCE);
    }

    /* Employee Language Permissions */
    public void addLanguagePermission() {
        checkPermission(EmployeeServiceResourceName.ADD_LANGUAGE);
    }

    public void getAllLanguagesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_LANGUAGES);
    }

    public void getLanguageByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_LANGUAGE_BY_ID);
    }

    public void getLanguagesByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_LANGUAGES_BY_EMPLOYEE_ID);
    }

    public void updateLanguagePermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_LANGUAGE);
    }

    public void deleteLanguagePermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_LANGUAGE);
    }

    /* Language Name Permissions */
    public void addLanguageNamePermission() {
        checkPermission(EmployeeServiceResourceName.ADD_LANGUAGE_NAME);
    }

    public void getAllLanguageNamesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_LANGUAGE_NAMES);
    }

    public void getLanguageNameByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_LANGUAGE_NAME_BY_ID);
    }

    public void updateLanguageNamePermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_LANGUAGE_NAME);
    }

    public void deleteLanguageNamePermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_LANGUAGE_NAME);
    }

    /* Employee Family Permissions */
    public void addFamilyPermission() {
        checkPermission(EmployeeServiceResourceName.ADD_FAMILY);
    }

    public void getAllFamiliesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_FAMILIES);
    }

    public void getFamilyByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_FAMILY_BY_ID);
    }

    public void getFamiliesByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_FAMILIES_BY_EMPLOYEE_ID);
    }

    public void updateFamilyPermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_FAMILY);
    }

    public void deleteFamilyPermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_FAMILY);
    }

    /* Employee Experience Permissions */
    public void addExperiencePermission() {
        checkPermission(EmployeeServiceResourceName.ADD_EXPERIENCE);
    }

    public void getAllExperiencesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_EXPERIENCES);
    }

    public void getExperienceByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_EXPERIENCE_BY_ID);
    }

    public void getExperiencesByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_EXPERIENCES_BY_EMPLOYEE_ID);
    }

    public void downloadExperienceDocumentPermission() {
        checkPermission(EmployeeServiceResourceName.DOWNLOAD_EXPERIENCE_DOCUMENT);
    }

    public void updateExperiencePermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_EXPERIENCE);
    }

    public void deleteExperiencePermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_EXPERIENCE);
    }

    /* Employee Education Permissions */
    public void addEducationPermission() {
        checkPermission(EmployeeServiceResourceName.ADD_EDUCATION);
    }

    public void getAllEducationsPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_EDUCATIONS);
    }

    public void getEducationByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_EDUCATION_BY_ID);
    }

    public void getEducationsByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_EDUCATIONS_BY_EMPLOYEE_ID);
    }

    public void downloadEducationDocumentPermission() {
        checkPermission(EmployeeServiceResourceName.DOWNLOAD_EDUCATION_DOCUMENT);
    }

    public void updateEducationPermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_EDUCATION);
    }

    public void deleteEducationPermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_EDUCATION);
    }

    /* Employee Address Permissions */
    public void addAddressPermission() {
        checkPermission(EmployeeServiceResourceName.ADD_ADDRESS);
    }

    public void getAllAddressesPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ALL_ADDRESSES);
    }

    public void getAddressByIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ADDRESS_BY_ID);
    }

    public void getAddressesByEmployeeIdPermission() {
        checkPermission(EmployeeServiceResourceName.GET_ADDRESSES_BY_EMPLOYEE_ID);
    }

    public void updateAddressPermission() {
        checkPermission(EmployeeServiceResourceName.UPDATE_ADDRESS);
    }

    public void deleteAddressPermission() {
        checkPermission(EmployeeServiceResourceName.DELETE_ADDRESS);
    }

    private void checkPermission(EmployeeServiceResourceName resourceName) {
        boolean hasPermission = roleConverter.hasPermission(resourceName.getValue());
        if (!hasPermission) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
