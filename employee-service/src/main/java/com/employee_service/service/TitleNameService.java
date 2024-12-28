package com.employee_service.service;

import com.employee_service.dto.clientDto.TenantDto;
import com.employee_service.dto.request.TitleNameRequest;
import com.employee_service.dto.response.TitleNameResponse;
import com.employee_service.model.TitleName;
import com.employee_service.exception.ResourceExistsException;
import com.employee_service.mapper.TitleNameMapper;
import com.employee_service.repository.TitleNameRepository;
import com.employee_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TitleNameService {

    private final TitleNameRepository titleNameRepository;
    private final TitleNameMapper titleNameMapper;
    private final ValidationUtil validationUtil;

    public TitleNameResponse addTitleName(UUID tenantId,
                                          TitleNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = titleNameMapper.mapToEntity(tenant, request);
        if (titleNameRepository.existsByTitleNameAndTenantId(
                request.getTitleName(), tenant.getId())) {
            throw new ResourceExistsException(
                    "Title name '" + request.getTitleName() + "' already exists");
        }
        titleName = titleNameRepository.save(titleName);
        return titleNameMapper.mapToDto(titleName);
    }

    public List<TitleNameResponse> getAllTitleNames(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<TitleName> titleNames = titleNameRepository.findAll();
        return titleNames.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(titleNameMapper::mapToDto)
                .toList();
    }

    public TitleNameResponse getTitleNameById(UUID tenantId,
                                          UUID titleId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), titleId);
        return titleNameMapper.mapToDto(titleName);
    }

    public TitleNameResponse updateTitleName(UUID tenantId,
                                             UUID titleId,
                                             TitleNameRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), titleId);
        titleName = titleNameMapper.updateEntity(titleName, request);
        titleName = titleNameRepository.save(titleName);
        return titleNameMapper.mapToDto(titleName);
    }

    public void deleteTitleName(UUID tenantId,
                                UUID titleId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        TitleName titleName = validationUtil.getTitleNameById(tenant.getId(), titleId);
        titleNameRepository.delete(titleName);
    }
}