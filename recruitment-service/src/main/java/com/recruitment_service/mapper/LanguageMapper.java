package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.LanguageNameDto;
import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.LanguageRequest;
import com.recruitment_service.dto.response.LanguageResponse;
import com.recruitment_service.model.Applicant;
import com.recruitment_service.model.Language;
import com.recruitment_service.enums.*;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguageMapper {

    private final ValidationUtil validationUtil;

    public Language mapToEntity(TenantDto tenant,
                                Applicant applicant,
                                LanguageRequest request) {

        LanguageNameDto languageName = validationUtil
                .getLanguageNameById(tenant.getId(), request.getLanguageNameId());

        Language language = new Language ();
        language.setTenantId(tenant.getId());
        language.setApplicant(applicant);
        language.setLanguageNameId(languageName.getId());
        language.setListening (Listening.valueOf(request.getListening ().toUpperCase()));
        language.setSpeaking (Speaking.valueOf(request.getSpeaking ().toUpperCase()));
        language.setWriting (Writing.valueOf(request.getWriting ().toUpperCase()));
        language.setReading (Reading.valueOf(request.getReading ().toUpperCase()));

        return language;
    }

    public LanguageResponse mapToDto(Language language) {

        LanguageResponse response = new LanguageResponse ();
        response.setId (language.getId ());
        response.setApplicantId(language.getApplicant().getId());
        response.setLanguageNameId (language.getLanguageNameId());
        response.setListening (language.getListening ().getListening());
        response.setSpeaking (language.getSpeaking ().getSpeaking());
        response.setWriting (language.getWriting ().getWriting());
        response.setReading (language.getReading ().getReading());
        response.setTenantId (language.getTenantId ());
        response.setCreatedAt (language.getCreatedAt ());
        response.setUpdatedAt (language.getUpdatedAt ());
        response.setCreatedBy (language.getCreatedBy ());
        response.setUpdatedBy (language.getUpdatedBy ());

        return response;
    }

    public Language updateLanguage(TenantDto tenant,
                                   Language language,
                                   LanguageRequest request) {

        LanguageNameDto languageName = validationUtil
                .getLanguageNameById(tenant.getId(), request.getLanguageNameId());

        if (request.getLanguageNameId() != null) {
            language.setLanguageNameId(languageName.getId());
        }
        if (request.getListening () != null) {
            language.setListening(Listening.valueOf(request.getListening().toUpperCase()));
        }
        if (request.getSpeaking () != null) {
            language.setSpeaking(Speaking.valueOf(request.getSpeaking().toUpperCase()));
        }
        if (request.getWriting () != null) {
            language.setWriting(Writing.valueOf(request.getWriting().toUpperCase()));
        }
        if (request.getReading () != null) {
            language.setReading(Reading.valueOf(request.getReading().toUpperCase()));
        }

        return language;
    }
}
