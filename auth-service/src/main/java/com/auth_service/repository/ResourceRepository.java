package com.auth_service.repository;

import com.auth_service.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ResourceRepository extends JpaRepository<Resource, UUID> {

    boolean existsByTenantIdAndResourceName(UUID tenantId, String resourceName);

    List<Resource> findByTenantId(UUID tenantId);

    Optional<Resource> findByTenantIdAndResourceName(UUID tenantId, String resourceName);

    List<Resource> findByTenantIdAndGroupName(UUID tenantId, String groupName);
}
