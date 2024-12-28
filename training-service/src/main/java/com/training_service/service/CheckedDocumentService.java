package com.training_service.service;

import com.training_service.dto.clientDto.TenantDto;
import com.training_service.dto.request.CheckedDocumentRequest;
import com.training_service.dto.response.CheckedDocumentResponse;
import com.training_service.exception.ResourceExistsException;
import com.training_service.exception.ResourceNotFoundException;
import com.training_service.model.CheckedDocument;
import com.training_service.model.PreServiceTrainee;
import com.training_service.mapper.CheckedDocumentMapper;
import com.training_service.repository.CheckedDocumentRepository;
import com.training_service.repository.PreServiceTraineeRepository;
import com.training_service.utility.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckedDocumentService {

    private final CheckedDocumentRepository checkedDocumentRepository;
    private final CheckedDocumentMapper checkedDocumentMapper;
    private final PreServiceTraineeRepository preServiceTraineeRepository;
    private final ValidationUtil validationUtil;

    @Transactional
    public CheckedDocumentResponse addCheckedDocument(UUID tenantId,
                                                      CheckedDocumentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = checkedDocumentMapper.mapToEntity(tenant, request);
        if (checkedDocumentRepository.existsByTenantIdAndDocumentName(
                tenant.getId(), request.getDocumentName())) {
            throw new ResourceExistsException(
                    "Document with name '" + request.getDocumentName() + "' already exists");
        }
        checkedDocument = checkedDocumentRepository.save(checkedDocument);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    public List<CheckedDocumentResponse> getAllCheckedDocuments(UUID tenantId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        List<CheckedDocument> checkedDocuments = checkedDocumentRepository.findAll();
        return checkedDocuments.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(checkedDocumentMapper::mapToDto)
                .toList();
    }

    public CheckedDocumentResponse getCheckedDocumentById(UUID tenantId,
                                                          UUID documentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    public List<CheckedDocumentResponse> getCheckedDocumentByTraineeId(UUID tenantId,
                                                                       UUID traineeId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        Set<CheckedDocument> checkedDocuments = preServiceTrainee.getCheckedDocuments();
        return checkedDocuments.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(checkedDocumentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public CheckedDocumentResponse updateCheckedDocument(UUID tenantId,
                                                         UUID documentId,
                                                         CheckedDocumentRequest request) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        checkedDocument = checkedDocumentMapper.updateEntity(checkedDocument, request);
        checkedDocument = checkedDocumentRepository.save(checkedDocument);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    @Transactional
    public void removeCheckedDocumentFromTrainee(UUID tenantId,
                                                 UUID traineeId,
                                                 UUID documentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = validationUtil.getTraineeById(tenant.getId(), traineeId);
        CheckedDocument documentToRemove = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        Set<CheckedDocument> checkedDocuments = preServiceTrainee.getCheckedDocuments();
        boolean removed = checkedDocuments.remove(documentToRemove);
        if (!removed) {
            throw new ResourceNotFoundException(
                    "Document not associated with the trainee '" + documentId + "'");
        }
        preServiceTrainee.setCheckedDocuments(checkedDocuments);
        preServiceTraineeRepository.save(preServiceTrainee);
    }

    @Transactional
    public void deleteCheckedDocument(UUID tenantId,
                                      UUID documentId) {

        TenantDto tenant = validationUtil.getTenantById(tenantId);
        CheckedDocument checkedDocument = validationUtil.getCheckedDocumentById(tenant.getId(), documentId);
        Set<PreServiceTrainee> preServiceTrainees = checkedDocument.getPreServiceTrainees();
        for (PreServiceTrainee trainee : preServiceTrainees) {
            trainee.getCheckedDocuments().remove(checkedDocument);
            preServiceTraineeRepository.save(trainee);
        }
        checkedDocumentRepository.delete(checkedDocument);
    }
}