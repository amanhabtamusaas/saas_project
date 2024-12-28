package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.EducationLevelDto;
import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.ShortlistCriteriaRequest;
import com.recruitment_service.dto.response.ShortlistCriteriaResponse;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.model.ShortlistCriteria;
import com.recruitment_service.enums.ExperienceType;
import com.recruitment_service.enums.Gender;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShortlistCriteriaMapper {

    private final ValidationUtil validationUtil;

    public ShortlistCriteria mapToEntity(TenantDto tenant,
                                         Recruitment recruitment,
                                         ShortlistCriteriaRequest request) {

        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());

        ShortlistCriteria shortlistCriteria = new ShortlistCriteria();
        shortlistCriteria.setTenantId(tenant.getId());
        shortlistCriteria.setRecruitment(recruitment);
        shortlistCriteria.setEducationLevelId(educationLevel.getId());
        shortlistCriteria.setCriteriaName(request.getCriteriaName());
        shortlistCriteria.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        shortlistCriteria.setTrainingOrCertificate(request.getTrainingOrCertificate());
        shortlistCriteria.setExperienceType(ExperienceType.valueOf(
                request.getExperienceType().toUpperCase()));
        shortlistCriteria.setCGPA(request.getCGPA());
        shortlistCriteria.setMinimumExperience(request.getMinimumExperience());
        shortlistCriteria.setMinimumAge(request.getMinimumAge());
        shortlistCriteria.setMaximumAge(request.getMaximumAge());
        shortlistCriteria.setOther(request.getOther());

        return shortlistCriteria;
    }

    public ShortlistCriteriaResponse mapToDto(ShortlistCriteria shortlistCriteria) {

        ShortlistCriteriaResponse response = new ShortlistCriteriaResponse();
        response.setId(shortlistCriteria.getId());
        response.setCriteriaName(shortlistCriteria.getCriteriaName());
        response.setGender(shortlistCriteria.getGender().getGender());
        response.setEducationLevelId(shortlistCriteria.getEducationLevelId());
        response.setTrainingOrCertificate(shortlistCriteria.getTrainingOrCertificate());
        response.setExperienceType(shortlistCriteria.getExperienceType().getExperienceType());
        response.setCGPA(shortlistCriteria.getCGPA());
        response.setMinimumExperience(shortlistCriteria.getMinimumExperience());
        response.setMinimumAge(shortlistCriteria.getMinimumAge());
        response.setMaximumAge(shortlistCriteria.getMaximumAge());
        response.setOther(shortlistCriteria.getOther());
        response.setRecruitmentId(shortlistCriteria.getRecruitment().getId());
        response.setTenantId (shortlistCriteria.getTenantId ());
        response.setCreatedAt (shortlistCriteria.getCreatedAt ());
        response.setUpdatedAt (shortlistCriteria.getUpdatedAt ());
        response.setCreatedBy (shortlistCriteria.getCreatedBy ());
        response.setUpdatedBy (shortlistCriteria.getUpdatedBy ());

        return response;
    }

    public ShortlistCriteria updateShortlistCriteria(TenantDto tenant,
                                                     ShortlistCriteria shortlistCriteria,
                                                     ShortlistCriteriaRequest request) {

        EducationLevelDto educationLevel = validationUtil
                .getEducationLevelById(tenant.getId(), request.getEducationLevelId());
        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());

        if (request.getRecruitmentId() != null) {
            shortlistCriteria.setRecruitment(recruitment);
        }
        if (request.getEducationLevelId() != null) {
            shortlistCriteria.setEducationLevelId(educationLevel.getId());
        }
        if (request.getCriteriaName() != null) {
            shortlistCriteria.setCriteriaName(request.getCriteriaName());
        }
        if (request.getGender() != null) {
            shortlistCriteria.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        }
        if (request.getTrainingOrCertificate() != null) {
            shortlistCriteria.setTrainingOrCertificate(request.getTrainingOrCertificate());
        }
        if (request.getExperienceType() != null) {
            shortlistCriteria.setExperienceType(ExperienceType.valueOf(
                    request.getExperienceType().toUpperCase()));
        }
        if (request.getCGPA() != null) {
            shortlistCriteria.setCGPA(request.getCGPA());
        }
        if (request.getMinimumExperience() != null) {
            shortlistCriteria.setMinimumExperience(request.getMinimumExperience());
        }
        if (request.getMinimumAge() != null) {
            shortlistCriteria.setMinimumAge(request.getMinimumAge());
        }
        if (request.getMaximumAge() != null) {
            shortlistCriteria.setMaximumAge(request.getMaximumAge());
        }
        if (request.getOther() != null) {
            shortlistCriteria.setOther(request.getOther());
        }

        return shortlistCriteria;
    }
}
