package com.insa.service;


import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TitleNameRequest;
import com.insa.dto.response.TitleNameResponse;
import com.insa.entity.TitleName;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TitleNameMapper;
import com.insa.repository.TitleNameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TitleNameService {

    private final TitleNameRepository titleNameRepository;
    private final TenantServiceClient tenantServiceClient;
    private final TitleNameMapper titleNameMapper;

    public TitleNameResponse addTitleName(Long tenantId,
                                          TitleNameRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TitleName titleName = titleNameMapper.mapToEntity(request);
        if (titleNameRepository.existsByTitleNameAndTenantId (
                request.getTitleName(), tenant.getId ())) {
            throw new ResourceExistsException(
                    "Title name '" + request.getTitleName() + "' already exists in the specified tenant");
        }
        titleName.setTenantId(tenant.getId());
        titleName = titleNameRepository.save(titleName);
        return titleNameMapper.mapToDto(titleName);
    }

    public List<TitleNameResponse> getAllTitleNames(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<TitleName> titleNames = titleNameRepository.findAll();
        return titleNames.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(titleNameMapper::mapToDto)
                .toList();
    }

    public TitleNameResponse getTitleName(Long tenantId,
                                          Long titleId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TitleName titleName = titleNameRepository.findById(titleId)
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title name not found with id '" + titleId + "' in the specified tenant"));
        return titleNameMapper.mapToDto(titleName);
    }

    public TitleNameResponse updateTitleName(Long tenantId,
                                             Long titleId,
                                             TitleNameRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TitleName titleName = titleNameRepository.findById(titleId)
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title name not found with id '" + titleId + "' in the specified tenant"));
        titleName = titleNameMapper.updateEntity(titleName, request);
        titleName = titleNameRepository.save(titleName);
        return titleNameMapper.mapToDto(titleName);
    }

    public void deleteTitleName(Long tenantId,
                                Long titleId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        TitleName titleName = titleNameRepository.findById(titleId)
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Title name not found with id '" + titleId + "' in the specified tenant"));
        titleNameRepository.delete(titleName);
    }
}
