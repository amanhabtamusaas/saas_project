package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.AdvertisementRequest;
import com.insa.dto.response.AdvertisementResponse;
import com.insa.entity.Advertisement;
import com.insa.entity.MediaType;
import com.insa.entity.Recruitment;
import com.insa.enums.RecruitmentStatus;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.AdvertisementMapper;
import com.insa.repository.AdvertisementRepository;
import com.insa.repository.MediaTypeRepository;
import com.insa.repository.RecruitmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final RecruitmentRepository recruitmentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final AdvertisementMapper advertisementMapper;
    private final MediaTypeRepository mediaTypeRepository;

    @Transactional
    public AdvertisementResponse createAdvertisement(Long tenantId,
                                                     AdvertisementRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findById(request.getRecruitmentId())
                .filter(rec -> rec.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with id '" + request.getRecruitmentId() + "' in the specified tenant"));
        Set<MediaType> mediaTypes = new HashSet<>();
        for (Long mediaTypeId : request.getMediaTypeIds()) {
            MediaType mediaType = mediaTypeRepository.findById(mediaTypeId)
                    .filter(media -> media.getTenantId().equals(tenant.getId()))
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Media type not found with id '" + mediaTypeId + "' in the specified tenant"));
            mediaTypes.add(mediaType);
        }
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create advertisement for non-approved recruitment.");
        }
        boolean advertisementExists = advertisementRepository.existsByRecruitment(recruitment);
        if (advertisementExists) {
            throw new ResourceExistsException("Advertisement already exists for this recruitment");
        }
        Advertisement advertisement = advertisementMapper.mapToEntity(request);
        advertisement.setTenantId(tenant.getId());
        advertisement.setRecruitment(recruitment);
        advertisement.setMediaTypes(mediaTypes);
        advertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.mapToDto(advertisement);
    }

    public List<AdvertisementResponse> getAllAdvertisements(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<Advertisement> advertisements = advertisementRepository.findAll();
        return advertisements.stream()
                .filter(adv -> adv.getTenantId().equals(tenant.getId()))
                .map(advertisementMapper::mapToDto)
                .toList();
    }

    public AdvertisementResponse getAdvertisementById(Long tenantId,
                                              Long advertisementId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .filter(adv -> adv.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Advertisement not found with id " + advertisementId +
                                " in the specified tenant " + tenant.getId()));
        return advertisementMapper.mapToDto(advertisement);
    }

    public AdvertisementResponse getAdvertisementByRecruitment(Long tenantId,
                                                               String vacancyNumber) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Recruitment recruitment = recruitmentRepository.findByVacancyNumber(vacancyNumber)
                .filter(adv -> adv.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Recruitment not found with vacancy number '" + vacancyNumber + "'"));
        Advertisement advertisement = advertisementRepository
                .findByRecruitmentId(recruitment.getId());
        return advertisementMapper.mapToDto(advertisement);
    }

    @Transactional
    public AdvertisementResponse updateAdvertisement(Long tenantId,
                                                     Long advertisementId,
                                                     AdvertisementRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Set<MediaType> mediaTypes = new HashSet<>();
        for (Long mediaTypeId : request.getMediaTypeIds()) {
            MediaType mediaType = mediaTypeRepository.findById(mediaTypeId)
                    .filter(media -> media.getTenantId().equals(tenant.getId()))
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "MediaType not found with id '" + mediaTypeId + "' in the specified tenant"));
            mediaTypes.add(mediaType);
        }
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .filter(adv -> adv.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Advertisement not found with id '" + advertisementId + "' in the specified tenant"));
        advertisement = advertisementMapper.updateAdvertisement(advertisement, request);
        if (request.getMediaTypeIds() != null) {
            advertisement.setMediaTypes(mediaTypes);
        }
        advertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.mapToDto(advertisement);
    }

    @Transactional
    public void removeMediaTypeFromAdvertisement(Long tenantId,
                                                 Long advertisementId,
                                                 Long mediaTypeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .filter(ad -> ad.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Advertisement not found with id '" + advertisementId + "' in the specified tenant"));
        MediaType mediaType = mediaTypeRepository.findById(mediaTypeId)
                .filter(media -> media.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Media type not found with id '" + mediaTypeId + "' in the specified tenant"));
        advertisement.getMediaTypes().remove(mediaType);
        advertisementRepository.save(advertisement);
    }
}
