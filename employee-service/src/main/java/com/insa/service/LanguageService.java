package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.LanguageRequest;
import com.insa.dto.response.LanguageResponse;
import com.insa.entity.Employee;
import com.insa.entity.Language;
import com.insa.entity.LanguageName;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.LanguageMapper;
import com.insa.repository.EmployeeRepository;
import com.insa.repository.LanguageNameRepository;
import com.insa.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageMapper languageMapper;
    private final EmployeeRepository employeeRepository;
    private final TenantServiceClient tenantServiceClient;
    private final LanguageNameRepository languageNameRepository;

    public LanguageResponse addLanguage(Long tenantId,
                                        Long employeeId,
                                        LanguageRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById (employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        LanguageName languageName = languageNameRepository.findById(request.getLanguageNameId())
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + request.getLanguageNameId() + "' in the specified tenant"));
        Language language = languageMapper.mapToEntity (request);
        language.setTenantId (tenant.getId ());
        language.setEmployee (employee);
        language.setLanguageName(languageName);
        language = languageRepository.save (language);
        return languageMapper.mapToDto (language);
    }

    public List<LanguageResponse> getAllLanguages(Long tenantId,
                                                  Long employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Language> languages = languageRepository.findByEmployeeId(employee.getId());
        return languages.stream ()
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .map (languageMapper::mapToDto)
                .toList();
    }

    public List<LanguageResponse> getEmployeeLanguages(Long tenantId,
                                                       String employeeId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        List<Language> languages = languageRepository.findByEmployeeId(employee.getId());
        return languages.stream ()
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .map (languageMapper::mapToDto)
                .toList();
    }

    public LanguageResponse getLanguageById(Long tenantId,
                                            Long employeeId,
                                            Long languageId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Language language = languageRepository.findById (languageId)
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .filter (lan -> lan.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Language not found with id '" + languageId + "' in the specified employee"));
        return languageMapper.mapToDto (language);
    }

    public LanguageResponse updateLanguage(Long tenantId,
                                           Long employeeId,
                                           Long languageId,
                                           LanguageRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        LanguageName languageName = languageNameRepository.findById(request.getLanguageNameId())
                .filter(language -> language.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Language not found with id '" + request.getLanguageNameId() + "' in the specified tenant"));
        Language language = languageRepository.findById (languageId)
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .filter (lan -> lan.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Language not found with id '" + languageId + "' in the specified employee"));
        language = languageMapper.updateLanguage (language, request);
        if (request.getLanguageNameId() != null) {
            language.setLanguageName(languageName);
        }
        language = languageRepository.save (language);
        return languageMapper.mapToDto (language);
    }

    public void deleteLanguage(Long tenantId,
                               Long employeeId,
                               Long languageId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Employee employee = employeeRepository.findById(employeeId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Employee not found with id '" + employeeId + "' in the specified tenant"));
        Language language = languageRepository.findById (languageId)
                .filter (lan -> lan.getTenantId ().equals (tenant.getId ()))
                .filter (lan -> lan.getEmployee ().getId ().equals (employee.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Language not found with id '" + languageId + "' in the specified employee"));
        languageRepository.delete (language);
    }
}
