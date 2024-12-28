package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.MediaTypeRequest;
import com.recruitment_service.dto.response.MediaTypeResponse;
import com.recruitment_service.model.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MediaTypeMapper {

    public MediaType mapToEntity(TenantDto tenant,
                                 MediaTypeRequest request) {

        MediaType mediaType = new MediaType();
        mediaType.setTenantId(tenant.getId());
        mediaType.setMediaTypeName(request.getMediaTypeName());
        mediaType.setDescription(request.getDescription());

        return mediaType;
    }

    public MediaTypeResponse mapToDto(MediaType mediaType) {

        MediaTypeResponse response = new MediaTypeResponse();
        response.setId(mediaType.getId());
        response.setMediaTypeName(mediaType.getMediaTypeName());
        response.setDescription(mediaType.getDescription());
        response.setTenantId(mediaType.getTenantId());
        response.setCreatedAt(mediaType.getCreatedAt());
        response.setUpdatedAt(mediaType.getUpdatedAt());
        response.setCreatedBy(mediaType.getCreatedBy());
        response.setUpdatedBy(mediaType.getUpdatedBy());

        return response;
    }

    public MediaType updateEntity(MediaType mediaType,
                                  MediaTypeRequest request) {

        if (request.getMediaTypeName() != null) {
            mediaType.setMediaTypeName(request.getMediaTypeName());
        }
        if (request.getDescription() != null) {
            mediaType.setDescription(request.getDescription());
        }

        return mediaType;
    }
}
