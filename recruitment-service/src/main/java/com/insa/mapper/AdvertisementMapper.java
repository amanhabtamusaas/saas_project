package com.insa.mapper;

import com.insa.dto.request.AdvertisementRequest;
import com.insa.dto.response.AdvertisementResponse;
import com.insa.dto.response.MediaTypeResponse;
import com.insa.entity.Advertisement;
import com.insa.enums.AnnouncementType;
import com.insa.enums.ApplicationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AdvertisementMapper {

    private final MediaTypeMapper mediaTypeMapper;

    public Advertisement mapToEntity(AdvertisementRequest request) {
        Advertisement advertisement = new Advertisement();
        advertisement.setStartDate(request.getStartDate());
        advertisement.setEndDate(request.getEndDate());
        advertisement.setAnnouncementType(AnnouncementType.valueOf(
                request.getAnnouncementType().toUpperCase()));
        advertisement.setOccurrence(request.getOccurrence());

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

    public Advertisement updateAdvertisement(Advertisement advertisement,
                                             AdvertisementRequest request) {
        if (request.getStartDate() != null)
            advertisement.setStartDate(request.getStartDate());
        if (request.getEndDate() != null)
            advertisement.setEndDate(request.getEndDate());
        if (request.getAnnouncementType() != null)
            advertisement.setAnnouncementType(AnnouncementType.valueOf(
                    request.getAnnouncementType().toUpperCase()));
        if (request.getOccurrence() != null) {
            advertisement.setOccurrence(request.getOccurrence());
        }

        return advertisement;
    }
}
