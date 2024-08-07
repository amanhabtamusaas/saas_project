package com.insa.mapper;

import com.insa.dto.request.LanguageRequest;
import com.insa.dto.response.LanguageResponse;
import com.insa.entity.Language;
import com.insa.enums.*;
import org.springframework.stereotype.Component;

@Component
public class LanguageMapper {

    public Language mapToEntity(LanguageRequest request) {
        Language language = new Language ();
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

    public Language updateLanguage(Language language, LanguageRequest request) {

        if (request.getListening () != null)
            language.setListening (Listening.valueOf(request.getListening ().toUpperCase()));
        if (request.getSpeaking () != null)
            language.setSpeaking (Speaking.valueOf(request.getSpeaking ().toUpperCase()));
        if (request.getWriting () != null)
            language.setWriting (Writing.valueOf(request.getWriting ().toUpperCase()));
        if (request.getReading () != null)
            language.setReading (Reading.valueOf(request.getReading ().toUpperCase()));

        return language;
    }
}
