package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.LanguageNameRequest;
import com.insa.dto.response.LanguageNameResponse;
import com.insa.entity.LanguageName;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.LanguageNameMapper;
import com.insa.repository.LanguageNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageNameService {

    private final LanguageNameRepository languageNameRepository;
    private final TenantServiceClient tenantServiceClient;
    private final LanguageNameMapper languageNameMapper;

    public LanguageNameResponse addLanguageName(Long tenantId,
                                                LanguageNameRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LanguageName languageName = languageNameMapper.mapToEntity(request);
        if (languageNameRepository.existsByLanguageNameAndTenantId (
                request.getLanguageName(), tenant.getId ())) {
            throw new ResourceExistsException(
                    "Language with name '" + request.getLanguageName() + "' already exists in the specified tenant");
        }
        languageName.setTenantId(tenant.getId());
        languageName = languageNameRepository.save(languageName);
        return languageNameMapper.mapToDto(languageName);
    }

    public List<LanguageNameResponse> getAllLanguageNames(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<LanguageName> languageNames = languageNameRepository.findAll();
        return languageNames.stream()
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .map(languageNameMapper::mapToDto)
                .toList();
    }

    public LanguageNameResponse getLanguageName(Long tenantId,
                                                Long languageId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LanguageName languageName = languageNameRepository.findById(languageId)
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + languageId + "' in the specified tenant"));
        return languageNameMapper.mapToDto(languageName);
    }

    public LanguageNameResponse updateLanguageName(Long tenantId,
                                                   Long languageId,
                                                   LanguageNameRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LanguageName languageName = languageNameRepository.findById(languageId)
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + languageId + "' in the specified tenant"));
        languageName = languageNameMapper.updateEntity(languageName, request);
        languageName = languageNameRepository.save(languageName);
        return languageNameMapper.mapToDto(languageName);
    }

    public void deleteLanguageName(Long tenantId,
                                   Long languageId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        LanguageName languageName = languageNameRepository.findById(languageId)
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + languageId + "' in the specified tenant"));
        languageNameRepository.delete(languageName);
    }
}
