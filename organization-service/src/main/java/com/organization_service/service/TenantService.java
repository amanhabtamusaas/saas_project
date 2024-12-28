package com.organization_service.service;

import com.organization_service.client.AuthServiceClient;
import com.organization_service.dto.requestDto.TenantRequest;
import com.organization_service.dto.responseDto.TenantResponse;
import com.organization_service.model.Tenant;
import com.organization_service.exception.ResourceExistsException;
import com.organization_service.exception.ResourceNotFoundException;
import com.organization_service.mapper.TenantMapper;
import com.organization_service.repository.TenantRepository;
import com.organization_service.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final AuthServiceClient authServiceClient;

    @Transactional
    public TenantResponse createTenant(TenantRequest tenantRequest, MultipartFile file) throws IOException {
        if (tenantRepository.existsByTenantName(tenantRequest.getTenantName())) {
            throw new ResourceExistsException(
                    "Tenant with Name " + tenantRequest.getTenantName() + " already exists");
        }
        Tenant tenant = tenantMapper.mapToEntity(tenantRequest, file);
        tenant = tenantRepository.save(tenant);
        //authServiceClient.addDefaultAndAdminRole(tenant.getId());
        //authServiceClient.addAdminUser(tenant.getId());
        //authServiceClient.addResource(tenant.getId());
        return tenantMapper.mapToDto(tenant);
    }

    public List<TenantResponse> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll();
        return tenants.stream()
                .map(tenantMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public TenantResponse getTenantById(UUID id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id '" + id + "'"));
        return tenantMapper.mapToDto(tenant);
    }

    public byte[] getLogoByTenantId(UUID tenantId, MediaType mediaType) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + tenantId + " "));

        switch (tenant.getLogoType()) {
            case "image/jpeg":
                mediaType = MediaType.valueOf(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf(MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf(MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                return "Unsupported file type".getBytes();
        }

        return FileUtils.decompressImage(tenant.getLogoBytes());
    }

    @Transactional
    public TenantResponse updateTenant(UUID id, TenantRequest tenantRequest, MultipartFile file) throws IOException {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + id + " "));

        tenant = tenantMapper.updateTenant(tenant, tenantRequest, file);
        tenant = tenantRepository.save(tenant);
        return tenantMapper.mapToDto(tenant);
    }

    @Transactional
    public void deleteTenant(UUID id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tenant not found with id: " + id + " "));
        authServiceClient.deleteAllResource(tenant.getId());
        tenantRepository.delete(tenant);
    }
}