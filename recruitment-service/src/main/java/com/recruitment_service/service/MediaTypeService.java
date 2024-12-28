package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.MediaTypeRequest;
import com.recruitment_service.dto.response.MediaTypeResponse;
import com.recruitment_service.model.Advertisement;
import com.recruitment_service.model.MediaType;
import com.recruitment_service.exception.ResourceExistsException;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.MediaTypeMapper;
import com.recruitment_service.repository.AdvertisementRepository;
import com.recruitment_service.repository.MediaTypeRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaTypeService {

    private final MediaTypeRepository mediaTypeRepository;
    private final MediaTypeMapper mediaTypeMapper;
    private final AdvertisementRepository advertisementRepository;
    private final ValidationUtil validationUtil;

    @Transactional
    public MediaTypeResponse addMediaType(UUID tenantId,
                                          MediaTypeRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        MediaType mediaType = mediaTypeMapper.mapToEntity(tenant, request);
        if (mediaTypeRepository
                .existsByMediaTypeNameAndTenantId(request.getMediaTypeName(), tenant.getId())) {
            throw new ResourceExistsException(
                    "Media type with name '" + request.getMediaTypeName() + "' already exists");
        }
        mediaType = mediaTypeRepository.save(mediaType);
        return mediaTypeMapper.mapToDto(mediaType);
    }

    public List<MediaTypeResponse> getAllMediaTypes(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<MediaType> mediaTypes = mediaTypeRepository.findAll();
        return mediaTypes.stream()
                .filter(media -> media.getTenantId().equals(tenant.getId()))
                .map(mediaTypeMapper::mapToDto)
                .toList();
    }

    public MediaTypeResponse getMediaTypeById(UUID tenantId,
                                          UUID mediaTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        MediaType mediaType = validationUtil.getMediaTypeById(tenant.getId(), mediaTypeId);
        return mediaTypeMapper.mapToDto(mediaType);
    }

    public List<MediaTypeResponse> getAllMediaTypesByAdvertisementId(UUID tenantId,
                                                                     UUID advertisementId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        if (!mediaTypeRepository.existsById(advertisementId)) {
            throw new ResourceNotFoundException(
                    "Advertisement not found with id '" + advertisementId + "'");
        }
        List<MediaType> mediaTypes = mediaTypeRepository.findMediaTypeByAdvertisementsId(advertisementId);
        return mediaTypes.stream()
                .filter(mediaType -> mediaType.getTenantId().equals(tenant.getId()))
                .map(mediaTypeMapper::mapToDto)
                .toList();
    }

    @Transactional
    public MediaTypeResponse updateMediaType(UUID tenantId,
                                             UUID mediaTypeId,
                                             MediaTypeRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        MediaType mediaType = validationUtil.getMediaTypeById(tenant.getId(), mediaTypeId);
        mediaType = mediaTypeMapper.updateEntity(mediaType, request);
        mediaType = mediaTypeRepository.save(mediaType);
        return mediaTypeMapper.mapToDto(mediaType);
    }

    @Transactional
    public void deleteMediaType(UUID tenantId,
                                UUID mediaTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        MediaType mediaType = validationUtil.getMediaTypeById(tenant.getId(), mediaTypeId);
        Set<Advertisement> advertisements = mediaType.getAdvertisements();
        for (Advertisement advertisement : advertisements) {
            advertisement.getMediaTypes().remove(mediaType);
            advertisementRepository.save(advertisement);
        }
        mediaTypeRepository.delete(mediaType);
    }
}