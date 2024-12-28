package com.organization_service.mapper;

import com.organization_service.dto.requestDto.TenantRequest;
import com.organization_service.dto.responseDto.TenantResponse;
import com.organization_service.model.Tenant;
import com.organization_service.utility.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class TenantMapper {

    public Tenant mapToEntity(TenantRequest tenantRequest, MultipartFile file) throws IOException {
        Tenant tenant = new Tenant ();
        tenant.setTenantName (tenantRequest.getTenantName ());
        tenant.setAbbreviatedName (tenantRequest.getAbbreviatedName ());
        tenant.setEstablishedYear (tenantRequest.getEstablishedYear ());
        tenant.setMission (tenantRequest.getMission ());
        tenant.setVision (tenantRequest.getVision ());

        if (file != null) {
            tenant.setLogoName (file.getOriginalFilename ());
            tenant.setLogoType (file.getContentType ());
            tenant.setLogoBytes (FileUtils.compressImage (file.getBytes ()));
        }

        return tenant;
    }

    public TenantResponse mapToDto(Tenant tenant) {
        TenantResponse response = new TenantResponse ();
        response.setId (tenant.getId ());
        response.setTenantName (tenant.getTenantName ());
        response.setAbbreviatedName (tenant.getAbbreviatedName ());
        response.setEstablishedYear (tenant.getEstablishedYear ());
        response.setMission (tenant.getMission ());
        response.setVision (tenant.getVision ());
        response.setLogoName (tenant.getLogoName ());
        response.setLogoType (tenant.getLogoType ());
        response.setLogoBytes (tenant.getLogoBytes ());
        response.setCreatedAt (tenant.getCreatedAt ());
        response.setUpdatedAt (tenant.getUpdatedAt ());
        response.setCreatedBy (tenant.getCreatedBy ());
        response.setUpdatedBy (tenant.getUpdatedBy ());

        return response;
    }

    public Tenant updateTenant(
            Tenant tenant, TenantRequest tenantRequest, MultipartFile file) throws IOException {
        if (tenantRequest.getTenantName () != null)
            tenant.setTenantName (tenantRequest.getTenantName ());
        if (tenantRequest.getAbbreviatedName () != null)
            tenant.setAbbreviatedName (tenantRequest.getAbbreviatedName ());
        if (tenantRequest.getEstablishedYear () != null)
            tenant.setEstablishedYear (tenantRequest.getEstablishedYear ());
        if (tenantRequest.getMission () != null)
            tenant.setMission (tenantRequest.getMission ());
        if (tenantRequest.getVision () != null)
            tenant.setVision (tenantRequest.getVision ());

        if (file != null) {
            tenant.setLogoName (file.getOriginalFilename ());
            tenant.setLogoType (file.getContentType ());
            tenant.setLogoBytes (FileUtils.compressImage (file.getBytes ()));
        }

        return tenant;
    }


}
