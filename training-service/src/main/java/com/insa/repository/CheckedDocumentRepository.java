package com.insa.repository;

import com.insa.entity.CheckedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckedDocumentRepository extends JpaRepository<CheckedDocument, Long> {

    boolean existsByTenantIdAndDocumentName(Long tenantId, String documentName);
}
