package com.recruitment_service.mapper;

import com.recruitment_service.dto.clientDto.TenantDto;
import com.recruitment_service.dto.request.AdvertisementRequest;
import com.recruitment_service.dto.response.AdvertisementResponse;
import com.recruitment_service.dto.response.MediaTypeResponse;
import com.recruitment_service.model.Advertisement;
import com.recruitment_service.enums.AnnouncementType;
import com.recruitment_service.enums.ApplicationStatus;
import com.recruitment_service.model.MediaType;
import com.recruitment_service.model.Recruitment;
import com.recruitment_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final MediaTypeMapper mediaTypeMapper;
    private final ValidationUtil validationUtil;

    public Advertisement mapToEntity(TenantDto tenant,
                                     Recruitment recruitment,
                                     AdvertisementRequest request) {

        Advertisement advertisement = new Advertisement();
        advertisement.setTenantId(tenant.getId());
        advertisement.setRecruitment(recruitment);
        advertisement.setStartDate(request.getStartDate());
        advertisement.setEndDate(request.getEndDate());
        advertisement.setAnnouncementType(AnnouncementType.valueOf(
                request.getAnnouncementType().toUpperCase()));
        advertisement.setOccurrence(request.getOccurrence());

        Set<MediaType> mediaTypes = new HashSet<>();
        for (UUID mediaTypeId : request.getMediaTypeIds()) {
            MediaType mediaType = validationUtil.getMediaTypeById(tenant.getId(), mediaTypeId);
            mediaTypes.add(mediaType);
        }
        advertisement.setMediaTypes(mediaTypes);

        return advertisement;
    }

    public AdvertisementResponse mapToDto(Advertisement advertisement) {

        AdvertisementResponse response = new AdvertisementResponse();
        response.setId(advertisement.getId());
        response.setStartDate(advertisement.getStartDate());
        response.setEndDate(advertisement.getEndDate());
        response.setAnnouncementType(advertisement.getAnnouncementType().getAnnouncementType());
        response.setOccurrence(advertisement.getOccurrence());
        response.setRecruitmentId(advertisement.getRecruitment().getId());
        response.setTenantId(advertisement.getTenantId());
        response.setCreatedAt(advertisement.getCreatedAt());
        response.setCreatedBy(advertisement.getCreatedBy());
        response.setUpdatedAt(advertisement.getUpdatedAt());
        response.setUpdatedBy(advertisement.getUpdatedBy());

        if (advertisement.getMediaTypes() != null) {
            List<MediaTypeResponse> mediaTypes = advertisement.getMediaTypes()
                    .stream()
                    .map(mediaTypeMapper::mapToDto)
                    .collect(Collectors.toList());
            response.setMediaTypes(mediaTypes);
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate = advertisement.getStartDate();
        LocalDateTime endDate = advertisement.getEndDate();
        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            response.setApplicationStatus(ApplicationStatus.OPEN.getApplicationStatus());
        } else if (now.isBefore(startDate)) {
            response.setApplicationStatus(ApplicationStatus.NOT_OPENED.getApplicationStatus());
        } else {
            response.setApplicationStatus(ApplicationStatus.CLOSED.getApplicationStatus());
        }

        return response;
    }

    public Advertisement updateAdvertisement(TenantDto tenant,
                                             Advertisement advertisement,
                                             AdvertisementRequest request) {

        if (request.getStartDate() != null) {
            advertisement.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            advertisement.setEndDate(request.getEndDate());
        }
        if (request.getAnnouncementType() != null) {
            advertisement.setAnnouncementType(AnnouncementType.valueOf(
                    request.getAnnouncementType().toUpperCase()));
        }
        if (request.getOccurrence() != null) {
            advertisement.setOccurrence(request.getOccurrence());
        }

        Set<MediaType> mediaTypes = new HashSet<>();
        for (UUID mediaTypeId : request.getMediaTypeIds()) {
            MediaType mediaType = validationUtil.getMediaTypeById(tenant.getId(), mediaTypeId);
            mediaTypes.add(mediaType);
        }
        if (request.getMediaTypeIds() != null) {
            advertisement.setMediaTypes(mediaTypes);
        }

        return advertisement;
    }
}
