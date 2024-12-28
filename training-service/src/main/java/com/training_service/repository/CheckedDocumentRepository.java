package com.training_service.repository;

import com.training_service.model.CheckedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CheckedDocumentRepository extends JpaRepository<CheckedDocument, UUID> {

    boolean existsByTenantIdAndDocumentName(UUID tenantId, String documentName);
}
