package com.insa.repository;

import com.insa.entity.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaTypeRepository extends JpaRepository<MediaType, Long> {

    boolean existsByMediaTypeNameAndTenantId(String mediaTypeName, Long tenantId);
    List<MediaType> findMediaTypeByAdvertisementsId(Long advertisementId);
}
