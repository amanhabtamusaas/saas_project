package com.insa.service;


import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.CheckedDocumentRequest;
import com.insa.dto.response.CheckedDocumentResponse;
import com.insa.dto.response.PreServiceTraineeResponse;
import com.insa.entity.CheckedDocument;
import com.insa.entity.PreServiceTrainee;
import com.insa.exception.ResourceExistsException;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.CheckedDocumentMapper;
import com.insa.mapper.PreServiceTraineeMapper;
import com.insa.repository.CheckedDocumentRepository;
import com.insa.repository.PreServiceTraineeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CheckedDocumentService {

    private final CheckedDocumentRepository checkedDocumentRepository;
    private final TenantServiceClient tenantServiceClient;
    private final CheckedDocumentMapper checkedDocumentMapper;
    private final PreServiceTraineeRepository preServiceTraineeRepository;

    @Transactional
    public CheckedDocumentResponse addCheckedDocument(Long tenantId,
                                          CheckedDocumentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        CheckedDocument checkedDocument = checkedDocumentMapper.mapToEntity(request);
        if (checkedDocumentRepository.existsByTenantIdAndDocumentName (
                tenant.getId(), request.getDocumentName())) {
            throw new ResourceExistsException(
                    "Document with name '" + request.getDocumentName() + "' already exists");
        }
        checkedDocument.setTenantId(tenant.getId());
        checkedDocument = checkedDocumentRepository.save(checkedDocument);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    public List<CheckedDocumentResponse> getAllCheckedDocuments(Long tenantId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        List<CheckedDocument> checkedDocuments = checkedDocumentRepository.findAll();
        return checkedDocuments.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(checkedDocumentMapper::mapToDto)
                .toList();
    }

    public CheckedDocumentResponse getCheckedDocument(Long tenantId,
                                          Long documentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        CheckedDocument checkedDocument = checkedDocumentRepository.findById(documentId)
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with id '" + documentId + "' in the specified tenant"));
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    public List<CheckedDocumentResponse> getCheckedDocumentByTrainee(Long tenantId,
                                                                     Long traineeId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trainee not found with id '" + traineeId + "' in the specified tenant"));
        Set<CheckedDocument> checkedDocuments = preServiceTrainee.getCheckedDocuments();
        return checkedDocuments.stream()
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .map(checkedDocumentMapper::mapToDto)
                .toList();
    }

    @Transactional
    public CheckedDocumentResponse updateCheckedDocument(Long tenantId,
                                             Long documentId,
                                             CheckedDocumentRequest request) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        CheckedDocument checkedDocument = checkedDocumentRepository.findById(documentId)
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with id '" + documentId + "' in the specified tenant"));
        checkedDocument = checkedDocumentMapper.updateEntity(checkedDocument, request);
        checkedDocument = checkedDocumentRepository.save(checkedDocument);
        return checkedDocumentMapper.mapToDto(checkedDocument);
    }

    @Transactional
    public void removeCheckedDocumentFromTrainee(Long tenantId,
                                             Long traineeId,
                                             Long documentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        PreServiceTrainee preServiceTrainee = preServiceTraineeRepository.findById(traineeId)
                .filter(trainee -> trainee.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pre Service Trainee not found with id '" + traineeId + "' in the specified tenant"));
        CheckedDocument documentToRemove = checkedDocumentRepository.findById(documentId)
                .filter(c -> c.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with id '" + documentId + "' in the specified tenant"));
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
    public void deleteCheckedDocument(Long tenantId,
                                      Long documentId) {

        TenantDto tenant = tenantServiceClient.getTenantById(tenantId);
        CheckedDocument checkedDocument = checkedDocumentRepository.findById(documentId)
                .filter(title -> title.getTenantId().equals(tenant.getId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Document not found with id '" + documentId + "' in the specified tenant"));
        Set<PreServiceTrainee> preServiceTrainees = checkedDocument.getPreServiceTrainees();
        for (PreServiceTrainee trainee : preServiceTrainees) {
            trainee.getCheckedDocuments().remove(checkedDocument);
            preServiceTraineeRepository.save(trainee);
        }
        checkedDocumentRepository.delete(checkedDocument);
    }
}
