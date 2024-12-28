package com.training_service.mapper;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.CheckedDocumentRequest;
import com.training_service.dto.response.CheckedDocumentResponse;
import com.training_service.model.CheckedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckedDocumentMapper {

    public CheckedDocument mapToEntity(TenantDto tenant,
                                       CheckedDocumentRequest request) {

        CheckedDocument checkedDocument = new CheckedDocument();
        checkedDocument.setTenantId(tenant.getId());
        checkedDocument.setDocumentName(request.getDocumentName());
        checkedDocument.setDescription(request.getDescription());

        return checkedDocument;
    }

    public CheckedDocumentResponse mapToDto(CheckedDocument checkedDocument) {

        CheckedDocumentResponse response = new CheckedDocumentResponse();
        response.setId(checkedDocument.getId());
        response.setDocumentName(checkedDocument.getDocumentName());
        response.setDescription(checkedDocument.getDescription());
        response.setTenantId(checkedDocument.getTenantId());
        response.setCreatedAt(checkedDocument.getCreatedAt());
        response.setUpdatedAt(checkedDocument.getUpdatedAt());
        response.setCreatedBy(checkedDocument.getCreatedBy());
        response.setUpdatedBy(checkedDocument.getUpdatedBy());

        return response;
    }

    public CheckedDocument updateEntity(CheckedDocument checkedDocument,
                                  CheckedDocumentRequest request) {

        if (request.getDocumentName() != null) {
            checkedDocument.setDocumentName(request.getDocumentName());
        }
        if (request.getDescription() != null) {
            checkedDocument.setDescription(request.getDescription());
        }

        return checkedDocument;
    }
}
