package com.insa.service;


import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.MediaTypeRequest;
import com.insa.dto.response.MediaTypeResponse;
import com.insa.entity.Advertisement;
import com.insa.entity.MediaType;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.MediaTypeMapper;
import com.insa.repository.AdvertisementRepository;
import com.insa.repository.MediaTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MediaTypeService {

    private final MediaTypeRepository mediaTypeRepository;
    private final TenantServiceClient tenantServiceClient;
    private final MediaTypeMapper mediaTypeMapper;
    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public MediaTypeResponse addMediaType(Long tenantId,
                                          MediaTypeRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        MediaType mediaType = mediaTypeMapper.mapToEntity(request);
        if (mediaTypeRepository.existsByMediaTypeNameAndTenantId (
                request.getMediaTypeName(), tenant.getId ())) {
            throw new ResourceExistsException(
                    "Media type with name '" + request.getMediaTypeName() +
                            "' already exists in the specified tenant");
        }
        mediaType.setTenantId(tenant.getId());
        mediaType = mediaTypeRepository.save(mediaType);
        return mediaTypeMapper.mapToDto(mediaType);
    }

    public List<MediaTypeResponse> getAllMediaTypes(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<MediaType> mediaTypes = mediaTypeRepository.findAll();
        return mediaTypes.stream()
                .filter(media -> media.getTenantId().equals(tenant.getId()))
                .map(mediaTypeMapper::mapToDto)
                .toList();
    }

    public MediaTypeResponse getMediaType(Long tenantId,
                                          Long mediaTypeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        MediaType mediaType = mediaTypeRepository.findById(mediaTypeId)
                .filter(media -> media.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Media type not found with id '" + mediaTypeId + "' in the specified tenant"));
        return mediaTypeMapper.mapToDto(mediaType);
    }

    public List<MediaTypeResponse> getAllMediaTypesByAdvertisementId(Long tenantId,
                                                             Long advertisementId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        if (!mediaTypeRepository.existsById(advertisementId)) {
            throw new ResourceNotFoundException("Advertisement not found with id '" +
                    advertisementId + "' in the specified tenant");
        }
        List<MediaType> mediaTypes = mediaTypeRepository
                .findMediaTypeByAdvertisementsId(advertisementId);
        return mediaTypes.stream()
                .filter(mediaType -> mediaType.getTenantId().equals(tenant.getId()))
                .map(mediaTypeMapper::mapToDto)
                .toList();
    }

    @Transactional
    public MediaTypeResponse updateMediaType(Long tenantId,
                                             Long mediaTypeId,
                                             MediaTypeRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        MediaType mediaType = mediaTypeRepository.findById(mediaTypeId)
                .filter(media -> media.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Media type not found with id '" + mediaTypeId + "' in the specified tenant"));
        mediaType = mediaTypeMapper.updateEntity(mediaType, request);
        mediaType = mediaTypeRepository.save(mediaType);
        return mediaTypeMapper.mapToDto(mediaType);
    }

    @Transactional
    public void deleteMediaType(Long tenantId,
                                Long mediaTypeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        MediaType mediaType = mediaTypeRepository.findById(mediaTypeId)
                .filter(media -> media.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Media type not found with id '" + mediaTypeId + "' in the specified tenant"));
        Set<Advertisement> advertisements = mediaType.getAdvertisements();
        for (Advertisement advertisement : advertisements) {
            advertisement.getMediaTypes().remove(mediaType);
            advertisementRepository.save(advertisement);
        }
        mediaTypeRepository.delete(mediaType);
    }
}
