package com.insa.mapper;

import com.insa.dto.request.CheckedDocumentRequest;
import com.insa.dto.response.CheckedDocumentResponse;
import com.insa.entity.CheckedDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckedDocumentMapper {

    public CheckedDocument mapToEntity(CheckedDocumentRequest request) {

        CheckedDocument checkedDocument = new CheckedDocument();
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
