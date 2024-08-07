package com.insa.mapper;

import com.insa.dto.request.ShortlistCriteriaRequest;
import com.insa.dto.response.ShortlistCriteriaResponse;
import com.insa.entity.ShortlistCriteria;
import com.insa.enums.ExperienceType;
import com.insa.enums.Gender;
import org.springframework.stereotype.Component;

@Component
public class ShortlistCriteriaMapper {

    public ShortlistCriteria mapToEntity(ShortlistCriteriaRequest request) {
        ShortlistCriteria shortlistCriteria = new ShortlistCriteria();
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

    public ShortlistCriteria updateShortlistCriteria(ShortlistCriteria shortlistCriteria,
                                                     ShortlistCriteriaRequest request) {
        if (request.getCriteriaName() != null)
            shortlistCriteria.setCriteriaName(request.getCriteriaName());
        if (request.getGender() != null)
            shortlistCriteria.setGender(Gender.valueOf(request.getGender().toUpperCase()));
        if (request.getTrainingOrCertificate() != null)
            shortlistCriteria.setTrainingOrCertificate(request.getTrainingOrCertificate());
        if (request.getExperienceType() != null)
            shortlistCriteria.setExperienceType(ExperienceType.valueOf(
                request.getExperienceType().toUpperCase()));
        if (request.getCGPA() != null)
            shortlistCriteria.setCGPA(request.getCGPA());
        if (request.getMinimumExperience() != null)
            shortlistCriteria.setMinimumExperience(request.getMinimumExperience());
        if (request.getMinimumAge() != null)
            shortlistCriteria.setMinimumAge(request.getMinimumAge());
        if (request.getMaximumAge() != null)
            shortlistCriteria.setMaximumAge(request.getMaximumAge());
        if (request.getOther() != null)
            shortlistCriteria.setOther(request.getOther());

        return shortlistCriteria;
    }
}
