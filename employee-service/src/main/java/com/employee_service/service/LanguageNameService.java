package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.LanguageNameRequest;
import com.employee_service.dto.response.LanguageNameResponse;
import com.employee_service.model.LanguageName;
import com.employee_service.exception.ResourceExistsException;
import com.employee_service.mapper.LanguageNameMapper;
import com.employee_service.repository.LanguageNameRepository;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LanguageNameService {

    private final LanguageNameRepository languageNameRepository;
    private final LanguageNameMapper languageNameMapper;
    private final ValidationUtil validationUtil;

    public LanguageNameResponse addLanguageName(UUID tenantId,
                                                LanguageNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = languageNameMapper.mapToEntity(tenant, request);
        if (languageNameRepository.existsByLanguageNameAndTenantId(
                request.getLanguageName(), tenant.getId())) {
            throw new ResourceExistsException(
                    "Language with name '" + request.getLanguageName() + "' already exists");
        }
        languageName = languageNameRepository.save(languageName);
        return languageNameMapper.mapToDto(languageName);
    }

    public List<LanguageNameResponse> getAllLanguageNames(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<LanguageName> languageNames = languageNameRepository.findAll();
        return languageNames.stream()
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .map(languageNameMapper::mapToDto)
                .toList();
    }

    public LanguageNameResponse getLanguageNameById(UUID tenantId,
                                                UUID languageId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = validationUtil.getLanguageNameById(tenant.getId(), languageId);
        return languageNameMapper.mapToDto(languageName);
    }

    public LanguageNameResponse updateLanguageName(UUID tenantId,
                                                   UUID languageId,
                                                   LanguageNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = validationUtil.getLanguageNameById(tenant.getId(), languageId);
        languageName = languageNameMapper.updateEntity(languageName, request);
        languageName = languageNameRepository.save(languageName);
        return languageNameMapper.mapToDto(languageName);
    }

    public void deleteLanguageName(UUID tenantId,
                                   UUID languageId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        LanguageName languageName = validationUtil.getLanguageNameById(tenant.getId(), languageId);
        languageNameRepository.delete(languageName);
    }
}