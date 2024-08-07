package com.insa.mapper;

import com.insa.dto.request.MediaTypeRequest;
import com.insa.dto.response.MediaTypeResponse;
import com.insa.entity.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MediaTypeMapper {

    public MediaType mapToEntity(MediaTypeRequest request) {

        MediaType mediaType = new MediaType();
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
