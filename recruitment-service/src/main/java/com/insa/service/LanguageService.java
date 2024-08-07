package com.insa.service;

import com.insa.dto.apiDto.LanguageNameDto;
import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.LanguageRequest;
import com.insa.dto.response.LanguageResponse;
import com.insa.entity.Applicant;
import com.insa.entity.Language;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.LanguageMapper;
import com.insa.repository.ApplicantRepository;
import com.insa.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final ApplicantRepository applicantRepository;
    private final TenantServiceClient tenantServiceClient;
    private final LanguageMapper languageMapper;
    private final EmployeeServiceClient employeeServiceClient;

    @Transactional
    public LanguageResponse addLanguage(Long tenantId,
                                        Long applicantId,
                                        LanguageRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        LanguageNameDto languageName = employeeServiceClient
                .getLanguageName(tenant.getId(), request.getLanguageNameId());
        Applicant applicant = applicantRepository.findById (applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Language language = languageMapper.mapToEntity (request);
        language.setTenantId (tenant.getId ());
        language.setLanguageNameId(languageName.getId());
        language.setApplicant (applicant);
        language = languageRepository.save (language);
        return languageMapper.mapToDto (language);
    }

    public List<LanguageResponse> getAllLanguages(Long tenantId,
                                                  Long applicantId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        List<Language> languages = languageRepository.findAll ();
        return languages.stream ()
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .filter (lan -> lan.getApplicant ().equals (applicant))
                .map (languageMapper::mapToDto)
                .toList();
    }

    public LanguageResponse getLanguageById(Long tenantId,
                                            Long applicantId,
                                            Long languageId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Language language = languageRepository.findById (languageId)
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .filter (lan -> lan.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Language not found with id '" + languageId + "' in the specified applicant"));
        return languageMapper.mapToDto (language);
    }

    @Transactional
    public LanguageResponse updateLanguage(Long tenantId,
                                           Long applicantId,
                                           Long languageId,
                                           LanguageRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        LanguageNameDto languageName = employeeServiceClient
                .getLanguageName(tenant.getId(), request.getLanguageNameId());
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Language language = languageRepository.findById (languageId)
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .filter (lan -> lan.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Language not found with id '" + languageId + "' in the specified applicant"));
        language = languageMapper.updateLanguage (language, request);
        if (request.getLanguageNameId() != null) {
            language.setLanguageNameId(languageName.getId());
        }
        language = languageRepository.save (language);
        return languageMapper.mapToDto (language);
    }

    @Transactional
    public void deleteLanguage(Long tenantId,
                               Long applicantId,
                               Long languageId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Language language = languageRepository.findById (languageId)
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .filter (lan -> lan.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Language not found with id '" + languageId + "' in the specified applicant"));
        languageRepository.delete (language);
    }
}
