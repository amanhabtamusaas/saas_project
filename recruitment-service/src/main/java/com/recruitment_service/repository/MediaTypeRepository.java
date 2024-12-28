package com.recruitment_service.repository;

import com.recruitment_service.model.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MediaTypeRepository extends JpaRepository<MediaType, UUID> {

    boolean existsByMediaTypeNameAndTenantId(String mediaTypeName, UUID tenantId);
    List<MediaType> findMediaTypeByAdvertisementsId(UUID advertisementId);
}
