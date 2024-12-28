package com.employee_service.mapper;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.LanguageRequest;
import com.employee_service.dto.response.LanguageResponse;
import com.employee_service.model.Employee;
import com.employee_service.model.Language;
import com.employee_service.enums.*;
import com.employee_service.model.LanguageName;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LanguageMapper {

    private final ValidationUtil validationUtil;

    public Language mapToEntity(TenantDto tenant,
                                Employee employee,
                                LanguageRequest request) {

        LanguageName languageName = validationUtil
                .getLanguageNameById(tenant.getId(), request.getLanguageNameId());

        Language language = new Language ();
        language.setTenantId(tenant.getId());
        language.setEmployee(employee);
        language.setLanguageName(languageName);
        language.setListening (Listening.valueOf(request.getListening ().toUpperCase()));
        language.setSpeaking (Speaking.valueOf(request.getSpeaking ().toUpperCase()));
        language.setWriting (Writing.valueOf(request.getWriting ().toUpperCase()));
        language.setReading (Reading.valueOf(request.getReading ().toUpperCase()));

        return language;
    }

    public LanguageResponse mapToDto(Language language) {

        LanguageResponse response = new LanguageResponse ();
        response.setId (language.getId ());
        response.setTenantId (language.getTenantId ());
        response.setLanguageNameId (language.getLanguageName().getId());
        response.setListening (language.getListening ().getListening());
        response.setSpeaking (language.getSpeaking ().getSpeaking());
        response.setWriting (language.getWriting ().getWriting());
        response.setReading (language.getReading ().getReading());
        response.setCreatedAt (language.getCreatedAt ());
        response.setUpdatedAt (language.getUpdatedAt ());
        response.setCreatedBy (language.getCreatedBy ());
        response.setUpdatedBy (language.getUpdatedBy ());
        response.setEmployeeId (language.getEmployee ().getId ());

        return response;
    }

    public Language updateLanguage(TenantDto tenant,
                                   Language language,
                                   LanguageRequest request) {

        LanguageName languageName = validationUtil
                .getLanguageNameById(tenant.getId(), request.getLanguageNameId());

        if (request.getLanguageNameId() != null) {
            language.setLanguageName(languageName);
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
