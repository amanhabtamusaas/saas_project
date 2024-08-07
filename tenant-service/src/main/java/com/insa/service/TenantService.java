package com.insa.service;

import com.insa.dto.requestDto.TenantRequest;
import com.insa.dto.responseDto.TenantResponse;
import com.insa.entity.Tenant;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TenantMapper;
import com.insa.repository.TenantRepository;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;

    public TenantResponse createTenant(
            TenantRequest tenantRequest, MultipartFile file) throws IOException {

        if (tenantRepository.existsByTenantName(tenantRequest.getTenantName())) {
            throw new ResourceExistsException (
                    "Tenant with Name " + tenantRequest.getTenantName () + " already exists");
        }

        Tenant tenant = tenantMapper.mapToEntity (tenantRequest, file);

        tenant = tenantRepository.save (tenant);
        return tenantMapper.mapToDto (tenant);
    }

    public List<TenantResponse> getAllTenants() {
        List<Tenant> tenants = tenantRepository.findAll ();
        return tenants.stream()
                .map (tenantMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public TenantResponse getTenantById(Long id) {
        Tenant tenant = tenantRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + id + " "));
        return tenantMapper.mapToDto (tenant);
    }

    public byte[] getLogoByTenantId(Long tenantId, MediaType mediaType) {
        Tenant tenant = tenantRepository.findById (tenantId)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + tenantId + " "));

        switch (tenant.getLogoType ()) {
            case "image/jpeg":
                mediaType = MediaType.valueOf (MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf (MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf (MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                return "Unsupported file type".getBytes ();
        }

        return FileUtils.decompressImage (tenant.getLogoBytes ());
    }

    public TenantResponse updateTenant(
            Long id, TenantRequest tenantRequest, MultipartFile file) throws IOException {

        Tenant tenant = tenantRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + id + " "));

        tenant = tenantMapper.updateTenant (tenant, tenantRequest, file);

        tenant = tenantRepository.save (tenant);
        return tenantMapper.mapToDto (tenant);
    }

    public void deleteTenant(Long id) {
        Tenant tenant = tenantRepository.findById (id)
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Tenant not found with id: " + id + " "));
        tenantRepository.delete (tenant);
    }
}
