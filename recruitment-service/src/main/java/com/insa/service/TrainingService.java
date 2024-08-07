package com.insa.service;

import com.insa.dto.apiDto.TenantDto;
import com.insa.dto.request.TrainingRequest;
import com.insa.dto.response.TrainingResponse;
import com.insa.entity.Applicant;
import com.insa.entity.Training;
import com.insa.exception.ResourceNotFoundException;
import com.insa.mapper.TrainingMapper;
import com.insa.repository.ApplicantRepository;
import com.insa.repository.TrainingRepository;
import com.insa.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;
    private final ApplicantRepository applicantRepository;
    private final TenantServiceClient tenantServiceClient;
    private final TrainingMapper trainingMapper;

    @Transactional
    public TrainingResponse addTraining(Long tenantId,
                                        Long applicantId,
                                        TrainingRequest request,
                                        MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById (applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow (() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Training training = trainingMapper.mapToEntity (request, file);
        training.setTenantId (tenant.getId ());
        training.setApplicant (applicant);
        training = trainingRepository.save (training);
        return trainingMapper.mapToDto (training);
    }

    public List<TrainingResponse> getAllTrainings(Long tenantId,
                                                  Long applicantId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        List<Training> trainings = trainingRepository.findAll ();
        return trainings.stream ()
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .filter (tr -> tr.getApplicant ().equals (applicant))
                .map (trainingMapper::mapToDto)
                .toList();
    }

    public TrainingResponse getTrainingById(Long tenantId,
                                            Long applicantId,
                                            Long trainingId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .filter (tr -> tr.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Training not found with id '" + trainingId + "' in the specified applicant"));
        return trainingMapper.mapToDto (training);
    }

    public byte[] getTrainingCertificateById(Long tenantId,
                                           Long applicantId,
                                           Long trainingId,
                                           MediaType mediaType) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(app -> app.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with '" + applicantId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (edu -> edu.getTenantId ().equals (tenant.getId ()))
                .filter (edu -> edu.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Education not found with id '" + trainingId + "' in the specified applicant"));

        switch (training.getCertificateType()) {
            case "application/pdf":
                mediaType = MediaType.valueOf (MediaType.APPLICATION_PDF_VALUE);
                break;
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                mediaType = MediaType.valueOf(
                        "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                break;
            case "image/jpeg":
                mediaType = MediaType.valueOf (MediaType.IMAGE_JPEG_VALUE);
                break;
            case "image/png":
                mediaType = MediaType.valueOf (MediaType.IMAGE_PNG_VALUE);
                break;
            case "image/gif":
                mediaType = MediaType.valueOf (MediaType.IMAGE_GIF_VALUE);
                break;
            default:
                return "Unsupported file type".getBytes ();
        }
        return FileUtils.decompressFile (training.getCertificateBytes ());
    }

    @Transactional
    public TrainingResponse updateTraining(Long tenantId,
                                           Long applicantId,
                                           Long trainingId,
                                           TrainingRequest request,
                                           MultipartFile file) throws IOException {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .filter (tr -> tr.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Training not found with id '" + trainingId + "' in the specified applicant"));
        training = trainingMapper.updateTraining (training, request, file);
        training = trainingRepository.save (training);
        return trainingMapper.mapToDto (training);
    }

    @Transactional
    public void deleteTraining(Long tenantId,
                               Long applicantId,
                               Long trainingId) {

        TenantDto tenant = tenantServiceClient.getTenantById (tenantId);
        Applicant applicant = applicantRepository.findById(applicantId)
                .filter(emp -> emp.getTenantId ().equals(tenant.getId ()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Applicant not found with id '" + applicantId + "' in the specified tenant"));
        Training training = trainingRepository.findById (trainingId)
                .filter (tr -> tr.getTenantId ().equals (tenant.getId ()))
                .filter (tr -> tr.getApplicant ().equals (applicant))
                .orElseThrow (() -> new ResourceNotFoundException (
                        "Training not found with id '" + trainingId + "' in the specified applicant"));
        trainingRepository.delete (training);
    }
}

