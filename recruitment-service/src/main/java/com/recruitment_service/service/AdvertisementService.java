package com.recruitment_service.service;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.AdvertisementRequest;
import com.recruitment_service.dto.response.AdvertisementResponse;
import com.recruitment_service.model.Advertisement;
import com.recruitment_service.model.MediaType;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.enums.RecruitmentStatus;
import com.recruitment_service.exception.ResourceExistsException;
import com.recruitment_service.exception.ResourceNotFoundException;
import com.recruitment_service.mapper.AdvertisementMapper;
import com.recruitment_service.repository.AdvertisementRepository;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapper;
    private final ValidationUtil validationUtil;

    @Transactional
    public AdvertisementResponse createAdvertisement(UUID tenantId,
                                                     AdvertisementRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentById(tenant.getId(), request.getRecruitmentId());
        if (!recruitment.getRecruitmentStatus().equals(RecruitmentStatus.APPROVED)) {
            throw new IllegalStateException("Cannot create advertisement for non-approved recruitment.");
        }
        boolean advertisementExists = advertisementRepository.existsByRecruitment(recruitment);
        if (advertisementExists) {
            throw new ResourceExistsException("Advertisement already exists for this recruitment");
        }
        Advertisement advertisement = advertisementMapper.mapToEntity(tenant, recruitment, request);
        advertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.mapToDto(advertisement);
    }

    public List<AdvertisementResponse> getAllAdvertisements(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<Advertisement> advertisements = advertisementRepository.findAll();
        return advertisements.stream()
                .filter(adv -> adv.getTenantId().equals(tenant.getId()))
                .map(advertisementMapper::mapToDto)
                .toList();
    }

    public AdvertisementResponse getAdvertisementById(UUID tenantId,
                                                      UUID advertisementId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Advertisement advertisement = getAdvertById(tenant.getId(), advertisementId);
        return advertisementMapper.mapToDto(advertisement);
    }

    public AdvertisementResponse getAdvertisementByVacancyNumber(UUID tenantId,
                                                               String vacancyNumber) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Recruitment recruitment = validationUtil
                .getRecruitmentByVacancyNumber(tenant.getId(), vacancyNumber);
        Advertisement advertisement = advertisementRepository.findByRecruitmentId(recruitment.getId());
        return advertisementMapper.mapToDto(advertisement);
    }

    @Transactional
    public AdvertisementResponse updateAdvertisement(UUID tenantId,
                                                     UUID advertisementId,
                                                     AdvertisementRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Advertisement advertisement = getAdvertById(tenant.getId(), advertisementId);
        advertisement = advertisementMapper.updateAdvertisement(tenant, advertisement, request);
        advertisement = advertisementRepository.save(advertisement);
        return advertisementMapper.mapToDto(advertisement);
    }

    @Transactional
    public void removeMediaTypeFromAdvertisement(UUID tenantId,
                                                 UUID advertisementId,
                                                 UUID mediaTypeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        Advertisement advertisement = getAdvertById(tenant.getId(), advertisementId);
        MediaType mediaType = validationUtil.getMediaTypeById(tenant.getId(), mediaTypeId);
        advertisement.getMediaTypes().remove(mediaType);
        advertisementRepository.save(advertisement);
    }

    private Advertisement getAdvertById(UUID tenantId, UUID advertisementId) {

        return advertisementRepository.findById(advertisementId)
                .filter(adv -> adv.getTenantId().equals(tenantId))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Advertisement not found with id '" + advertisementId + "'"));
    }
}